package com.mefi.backend.common.auth;

import com.mefi.backend.common.util.JWTUtil;
import com.mefi.backend.db.entity.Token;
import com.mefi.backend.db.entity.User;
import com.mefi.backend.db.repository.TokenRepository;
import com.mefi.backend.db.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    // 생성자 주입 (Security 기본 로그인 URL 수정을 위해 직접 작성)
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, TokenRepository tokenRepository, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.setFilterProcessesUrl("/users/login");
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

            // 요청을 가로채 클라이언트 요청에서 email, password 추출
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // email, password 검증하기 위해 authToken 객체에 담기
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

            // 검증을 위해 authToken을 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공시 실행하는 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        // 유저 확인 및 토큰 생성은 컨트롤러 맵핑을 통해 수행
        System.out.println("login success : request = " + request);

        // 유저 정보
        String email = request.getParameter("email");

        // JWT 발급 (아이디, 직책, 만료 기간)
        // accessToken, refreshToken 생성
        // accessToken : 테스트 - 5분, 추후 - Duration.ofHours(1)
        // refreshToken : 테스트 - 10분, 추후 - Duration.ofHours(6)
        String accessToken = jwtUtil.createJwt(email, null, 60*5*1000L);
        String refreshToken = jwtUtil.createJwt(email, null, 60*10*1000L);

        // 아이디 존재, 유저 정보까지 일치된 상태
        User user = userRepository.findByEmail(email);

        // 유저 식별 아이디로 토큰 조회
        Optional<Token> token = tokenRepository.findByUserId(user.getId());

        // 유저 식별 아이디로 토큰 존재 여부 확인
        if(token.isPresent()) {

            // 유저가 토큰이 존재
            token.get().updateRefreshToken(refreshToken);

            // DB 저장
            tokenRepository.save(token.get());
        }

        else {

            // 유저가 토큰이 없음
            Token newToken = Token.builder()
                    .userId(user.getId())
                    .refreshToken(refreshToken)
                    .build();

            // DB 저장
            tokenRepository.save(newToken);
        }

        // 응답에 저장 (상태, 헤더)
        response.setStatus(HttpStatus.OK.value());
        response.addHeader("accessToken",accessToken);
        response.addHeader("refreshToken",refreshToken);
        response.getWriter().write("Success");
    }

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        //로그인 실패시 401 응답 코드 반환
        response.setStatus(401);
        System.out.println("login fail : request = " + request);
    }
}