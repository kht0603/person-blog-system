package com.example.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 允许所有请求访问，关闭默认登录和CSRF
        http.authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 配置所有请求允许访问
                )
                .csrf(csrf -> csrf.disable()); // 关闭CSRF防护
        return http.build();
    }
}