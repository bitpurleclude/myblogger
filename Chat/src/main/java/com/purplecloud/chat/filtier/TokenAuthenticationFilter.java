package com.purplecloud.chat.filtier;



import com.purplecloud.chat.bean.ResultData;
import com.purplecloud.chat.bean.ReturnCode;
import com.purplecloud.chat.bean.SecurityAccessConstant;
import com.purplecloud.chat.util.JWTUtil;
import com.purplecloud.chat.util.LoginUserInfoHelper;
import com.purplecloud.chat.util.ResponseUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 15517
 */
@Order(1)
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    public TokenAuthenticationFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("uri:"+request.getRequestURI());
        System.out.println("uri:"+request.getRequestURI());
        //获取包含权限的authentication
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if(null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtils ResponseUtil=new ResponseUtils();
            ResponseUtil.out(response, ResultData.fail(ReturnCode.RC401.getCode(), ReturnCode.RC401.getMessage()));
        }
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        //请求头是否有token
        String token = JWTUtil.getToken(request.getHeader(SecurityAccessConstant.HEADER_NAME_TOKEN));
        System.out.println("token:"+token);
        if(null != token) {
            String email = JWTUtil.getEmail(token);
            Long userId = JWTUtil.getUserId(token);
            List<String> permission = JWTUtil.getPermission(token);

            if(null != permission) {
                //当前用户信息放到ThreadLocal里面
                LoginUserInfoHelper.setUserId(userId);
                LoginUserInfoHelper.setEmail(email);

                //把权限数据转换要求集合类型 List<SimpleGrantedAuthority>
                List<SimpleGrantedAuthority> collect = permission.stream().map(val -> new SimpleGrantedAuthority(val)).collect(Collectors.toList());

                return new UsernamePasswordAuthenticationToken(email, null, collect);
            }

        }
        return null;
    }
}

