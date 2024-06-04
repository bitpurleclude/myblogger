package com.purplecloud.chat.config;

import com.purplecloud.chat.filtier.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity //@EnableWebSecurity是开启SpringSecurity的默认行为
@EnableMethodSecurity //启用方法级别鉴权
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter authenticationJwtTokenFilter() {
        return new TokenAuthenticationFilter();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用basic明文验证
                .httpBasic((httpBasic) -> httpBasic.disable())
                // 前后端分离架构不需要csrf保护
                .csrf((csrf) -> csrf.disable())
                // 禁用默认登录页
                .formLogin((formLogin) -> formLogin.disable())
                // 禁用默认登出页
                .logout((logout) -> logout.disable())
                // 前后端分离是无状态的，不需要session了，直接禁用。
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // 允许所有OPTIONS请求
                        .requestMatchers(HttpMethod.OPTIONS,"**").permitAll()
                        // 允许 SpringMVC 的默认错误地址匿名访问
                        .requestMatchers("/error").permitAll()
                        // 允许任意请求被已登录用户访问，不检查Authority
                        .anyRequest().authenticated())
                // 加我们自定义的过滤器，替代UsernamePasswordAuthenticationFilter
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
