package com.purplecloud.www.controller;

import com.purplecloud.www.bean.SecurityAccessConstant;
import com.purplecloud.www.bean.User;
import com.purplecloud.www.impl.UserServiceImpl;
import com.purplecloud.www.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("/userPermission")
@RestController
public class UserPermissionController {
    @Autowired
    private UserServiceImpl userService;
    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User login = userService.login(user.getEmail(), user.getPassword());
        ResponseEntity<?> token = getResponseEntity(user, login);
        if (token != null) return token;
        // 如果登录失败，返回一个错误消息和一个失败的HTTP状态码
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
    }
    @RequestMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User register = userService.register(user);
        ResponseEntity<?> token = getResponseEntity(user, register);
        if (token != null) return token;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Register failed");
    }

    private ResponseEntity<?> getResponseEntity(User user, User register) {
        if (register != null) {
            userService.initUser(register);
            Collection<? extends GrantedAuthority> authorities = register.getAuthorities();
            String[] token = JWTUtil.createToken((long) register.getId(), user.getEmail(), authorities);
            return ResponseEntity.ok().body(new TokenResponse(token[0]));
        }
        return null;
    }

    @RequestMapping(value = "/getIdFromToken", method = RequestMethod.GET)
    public Long getIdFromToken(HttpServletRequest request) {
        String token = JWTUtil.getToken(request.getHeader(SecurityAccessConstant.HEADER_NAME_TOKEN));
        if(null != token) {
            return JWTUtil.getUserId(token);
        }

        return null;
    }


}
// 一个简单的类，用于包装JWT令牌
class TokenResponse {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}