package com.purplecloud.www.config;

import com.purplecloud.www.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SpringSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 使用新的方法替换http.cors()
                .addFilterBefore(loginFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))), UsernamePasswordAuthenticationFilter.class)
                .csrf((csrf) -> csrf.disable())
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/userPermission/login1").permitAll()
                        .successHandler(new CustomAuthenticationSuccessHandler())
                )
                .authorizeHttpRequests(authorize -> authorize
                        //.requestMatchers("/*").permitAll()
                        //.requestMatchers("/user/authentication/*").permitAll()
                        //.requestMatchers("/hello").permitAll()
                        //.requestMatchers("/html/SvgDraw.html").permitAll()
                        .anyRequest().permitAll()
                )
                ;

        return http.build();
    }
    CustomUsernamePasswordAuthenticationFilter loginFilter(AuthenticationManager authenticationManager) throws Exception {
        CustomUsernamePasswordAuthenticationFilter loginFilter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager);
        loginFilter.setFilterProcessesUrl("/userPermission/login1");
        loginFilter.setUsernameParameter("email");
        loginFilter.setPasswordParameter("password");
//        loginFilter.setAuthenticationManager();
        return loginFilter;
    }
}
