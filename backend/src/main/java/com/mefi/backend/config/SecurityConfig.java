package com.mefi.backend.config;

import com.mefi.backend.common.auth.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    // AuthenticationManager 등록
    // 데이터베이스에서 회원 정보를 가져와 검증 수행하는 클래스
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // BCryptPaasswordEncoder 등록
    // 비밀번호 인코딩(암호화) 기능을 구현한 클래스
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
                        // Swagger 접근 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // 이외 인증된 사용자만 접근 허용
                        .anyRequest().authenticated());

        // 필터 등록
        http
                // 해당 필터 자리에서 수행 (수행 할 필터, 대체 필터 자리)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        // 세션 설정 (JWT를 통한 인증/인가를 위해서 세션을 STATELESS 상태로 설정)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}