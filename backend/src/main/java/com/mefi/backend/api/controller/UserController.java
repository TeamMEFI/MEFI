package com.mefi.backend.api.controller;

import com.mefi.backend.api.request.JoinReqDto;
import com.mefi.backend.api.service.TokenService;
import com.mefi.backend.api.service.UserService;
import com.mefi.backend.common.auth.CustomUserDetails;
import com.mefi.backend.common.model.BaseResponseBody;
import com.mefi.backend.db.entity.Token;
import com.mefi.backend.db.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name="1.USER", description="USER API")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @Operation(summary = "회원가입", description = "/users\n\n 사용자의 정보를 통해 회원가입 한다.")
    @PostMapping("")
    @ApiResponse(responseCode = "200", description = "성공 \n\n Success 반환")
    public ResponseEntity<? extends BaseResponseBody> join(@RequestBody JoinReqDto joinReqDto) {

        // 회원가입
        userService.join(joinReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0,"Success"));
    }

    @Operation(summary = "회원탈퇴", description = "/users\n\n 사용자는 회원탈퇴를 한다.")
    @DeleteMapping("")
    @ApiResponse(responseCode = "200", description = "성공 \n\n Success 반환")
    public ResponseEntity<? extends BaseResponseBody> withdraw(Authentication authentication) {

        // 로그인된 유저 정보 조회
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 유저 식별 ID로 유저 정보 조회
        User user = userService.findById(userDetails.getUserId());

        // 유저 제거
        userService.withdraw(user);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0,"Success"));
    }

    @Operation(summary = "로그아웃", description = "/users/logout\n\n 사용자는 로그아웃을 한다.")
    @PostMapping("/logout")
    @ApiResponse(responseCode = "200", description = "성공 \n\n Success 반환")
    public ResponseEntity<? extends BaseResponseBody> logout(Authentication authentication) {

        // 로그인된 유저 정보 조회
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 식별 ID로 리프레시 토큰 제거
        Token token = tokenService.findByUserId(userDetails.getUserId());
        tokenService.deleteRefreshToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0,"Success"));
    }
}