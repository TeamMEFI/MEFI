package com.mefi.backend.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    
    // 에러 열거

    // 회원
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "U-001", "존재하지 않는 회원입니다."),
    CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "U-002", "인증 코드가 일치하지 않습니다."),

    // 토큰
    UNEXPECTED_TOKEN(HttpStatus.BAD_REQUEST, "T-001", "만료되었거나 존재하지 않는 토큰입니다.");

    // 상태, 에러 코드, 메시지
    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    // 생성자
    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}