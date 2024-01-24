package com.mefi.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // csrf disable (CSRF 비활성화)
        http
                .csrf((auth) -> auth.disable());

        // form login disable (폼 로그인 비활성화)
        http
                .formLogin((auth) -> auth.disable());

        // http basic 인증 disable (기본 인증 비활성화)
        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth

                        // 작성된 경로 모든 사용자 접근 허용
                        .requestMatchers("/", "/users").permitAll()

                        // 이외 인증된 사용자만 접근 허용
                        .anyRequest().authenticated());

        // 세션 설정 (JWT를 통한 인증/인가를 위해서 세션을 STATELESS 상태로 설정)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}