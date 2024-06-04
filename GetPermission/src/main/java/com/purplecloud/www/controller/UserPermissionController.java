package com.purplecloud.www.controller;

import com.purplecloud.www.bean.User;
import com.purplecloud.www.impl.UserServiceImpl;
import com.purplecloud.www.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        if (login != null) {
            userService.initUser(login);
            Collection<? extends GrantedAuthority> authorities = login.getAuthorities();
            String[] token = JWTUtil.createToken((long) login.getId(), user.getEmail(), authorities);
            // 返回一个包含令牌的对象，以及一个成功的HTTP状态码
            return ResponseEntity.ok().body(new TokenResponse(token[0]));
        }
        // 如果登录失败，返回一个错误消息和一个失败的HTTP状态码
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
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