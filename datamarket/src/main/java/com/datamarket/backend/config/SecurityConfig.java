package com.datamarket.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF cho REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Cho phép register/login
                        .anyRequest().authenticated() // Còn lại yêu cầu xác thực
                )
                .formLogin(form -> form.disable()) // Tắt form login mặc định
                .httpBasic(httpBasic -> httpBasic.disable()); // Tắt basic auth

        return http.build();
    }
}
