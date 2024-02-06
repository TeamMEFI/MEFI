package com.mefi.backend.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mefi.backend.api.request.LoginReqDto;
import com.mefi.backend.api.response.LoginResDto;
import com.mefi.backend.common.util.JWTUtil;
import com.mefi.backend.db.entity.Token;
import com.mefi.backend.db.entity.User;
import com.mefi.backend.db.repository.TokenRepository;
import com.mefi.backend.db.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private ObjectMapper mapper;

    // 생성자 주입 (Security 기본 로그인 URL 수정을 위해 직접 작성)
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, TokenRepository tokenRepository, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.setFilterProcessesUrl("/api/users/login");
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        // json format login

        // LoginReqDto 변환을 위한 Mapper
        mapper = new ObjectMapper();

        // email, password 검증하기 위해 데이터를 담을 authToken 객체
        UsernamePasswordAuthenticationToken authToken;

        // request 내부 json 추출 후 LoginReqDto 객체 변환
        try {
            LoginReqDto loginReqDto = mapper.readValue(
                    request.getReader().lines().collect(Collectors.joining()), LoginReqDto.class);

            // email, password 검증하기 위해 authToken 객체에 데이터 담기
            authToken = new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword(), null);

        } catch (IOException e) {
            throw new AuthenticationServiceException("Request Content-Type is not application/json");
        }

        // 검증을 위해 authToken을 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공시 실행하는 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        System.out.println("login success");

        // 로그인된 유저 확인 및 유저 엔티티 생성
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        // JWT 발급 (아이디, 직책, 만료 기간)
        // accessToken, refreshToken 생성
        // accessToken : 테스트 - 5분, 추후 - Duration.ofHours(1)
        // refreshToken : 테스트 - 10분, 추후 - Duration.ofHours(6)
        String accessToken = jwtUtil.createJwt(user.getEmail(), null, 60*5*1000L);
        String refreshToken = jwtUtil.createJwt(user.getEmail(), null, 60*10*1000L);

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

            // 유저가 토큰이 없는 경우
            Token newToken = Token.builder()
                    .userId(user.getId())
                    .refreshToken(refreshToken)
                    .build();

            // DB 저장
            tokenRepository.save(newToken);
        }

        // 응답에 저장 (헤더, 상태)
        response.addHeader("accessToken",accessToken);
        response.addHeader("refreshToken",refreshToken);
        response.setStatus(200);

        // 응답에 저장 (바디)
        Map<String,LoginResDto> reposenBody = new HashMap<>();
        LoginResDto loginResDto = new LoginResDto(user.getEmail(),user.getName(),user.getDept(),user.getPosition());
        reposenBody.put("dataBody",loginResDto);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(reposenBody));
        response.getWriter().flush();
    }

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        //로그인 실패시 401 응답 코드 반환
        response.setStatus(401);
        System.out.println("login fail : request = " + request);
    }
}