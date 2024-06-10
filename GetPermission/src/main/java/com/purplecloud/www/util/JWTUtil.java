package com.purplecloud.www.util;

import com.google.gson.Gson;
import com.purplecloud.www.bean.SecurityAccessConstant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.gson.io.GsonSerializer;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTUtil {
    private static final long tokenExpiration = 20 * 60 * 1000; // 20min过期
    private static final long tokenRefreshExpiration = 12 * 60 * 60 * 1000; // 12小时过期
    private static final String tokenSignKey = generateKey(); //密钥

    private static String generateKey() {
        String password = "Ljr2287699363@Ljr2287699363@Ljr2287699363@Ljr2287699363@Ljr2287699363@Ljr2287699363@";
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(passwordBytes);
    }
    private static String buildToken(Long userId, String email, List<String> permission , long timeToLive){
        return Jwts.builder()
                .setSubject("AUTH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + timeToLive))
                .claim("userId", userId)
                .claim("email", email)
                .claim("permission", permission)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .serializeToJsonWith(new GsonSerializer<>(new Gson()))
                .compact();
    }
    private static String buildToken(Long userId, String email, Collection<? extends GrantedAuthority> authorities, long timeToLive){
        List<String> permissions = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject("AUTH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + timeToLive))
                .claim("userId", userId)
                .claim("email", email)
                .claim("permission", permissions)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }
    public static String[] createToken(Long userId, String email, Collection<? extends GrantedAuthority> authorities) {
        String token = buildToken(userId,email,authorities,tokenExpiration);
        //token过期时可以刷新长期token
        String refreshToken = buildToken(userId,email,authorities,tokenRefreshExpiration);

        return new String[]{token , refreshToken};
    }
    public static String[] createToken(Long userId, String email, List<String> permission) {
        String token = buildToken(userId,email,permission,tokenExpiration);
        //token过期时可以刷新长期token
        String refreshToken = buildToken(userId,email,permission,tokenRefreshExpiration);

        return new String[]{token , refreshToken};
    }

    // 原始token过期时刷新token 而refreshToken保持不变(如果refresh都过期则需重新登录)
    public static String refresh(String refreshToken){
        return buildToken( getUserId(refreshToken) ,getEmail(refreshToken)
                , getPermission(refreshToken) , tokenExpiration);
    }

    // 去掉前缀
    public static String getToken(String token){
        if(token == null) {
            return null;
        }

        if(token.startsWith(SecurityAccessConstant.TOKEN_PREFIX)) {
            return token.replace(SecurityAccessConstant.TOKEN_PREFIX,"");
        }

        //没带前缀的认为是无效token
        return null;
    }

    // 获取当前token过期时间
    public static Date getExpirationDate(String token) {
        if(StringUtil.isBlank(token)) {
            return null;
        }

        Jws<Claims> claimsJws = getClaims(token);
        Claims claims = claimsJws.getBody();
        return claims.getExpiration();
    }

    //判断当前token是否过期
    public static boolean isOutDate(String token){
        try {
            Jws<Claims> claimsJws = getClaims(token);
            Date expirationDate = claimsJws.getBody().getExpiration();
            return expirationDate.before(new Date());
        } catch (JwtException e) {
            // JWT token无效或已损坏
            return true;
        }
    }

    public static Long getUserId(String token) {
        try {
            if (token == null || "".equals(token)) {
                return null;
            }

            Jws<Claims> claimsJws = getClaims(token);
            Claims claims = claimsJws.getBody();
            Double userId = (Double) claims.get("userId");
            return userId.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getEmail(String token) {
        try {
            if (token == null || token == "") {
                return "";
            }

            Jws<Claims> claimsJws = getClaims(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("email");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getPermission(String token) {
        try {
            if (token == null || token == "") {
                return null;
            }

            Jws<Claims> claimsJws = getClaims(token);
            Claims claims = claimsJws.getBody();
            return (List<String>) claims.get("permission");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static  Jws<Claims> getClaims(String token){
        byte[] decodedKey = Base64.getDecoder().decode(tokenSignKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA512");

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(originalKey).parseClaimsJws(token);
        return claimsJws;
    }
}

