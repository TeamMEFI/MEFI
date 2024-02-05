package com.mefi.backend.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    
    // 에러 열거

    // 회원
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "U-001", "존재하지 않는 회원입니다."),
    EMAIL_NOT_EXIST(HttpStatus.BAD_REQUEST, "U-002", "존재하지 않는 이메일입니다."),
    EMAIL_EXIST(HttpStatus.BAD_REQUEST, "U-003", "이미 사용 중인 이메일입니다."),
    CODE_TIME_EXPIRED(HttpStatus.BAD_REQUEST, "U-004", "인증 시간이 초과하였습니다."),
    CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "U-005", "인증 코드가 일치하지 않습니다."),
    CODE_NOT_EXIST(HttpStatus.BAD_REQUEST, "U-006", "인증 코드가 유효하지 않습니다."),
    SAME_AS_BEFORE(HttpStatus.BAD_REQUEST, "U-007", "이전 정보와 동일합니다."),
    CATEGORY_NOT_EXIST(HttpStatus.BAD_REQUEST, "U-008", "존재하지 않는 항목입니다."),

    // 토큰
    UNEXPECTED_TOKEN(HttpStatus.BAD_REQUEST, "T-001", "만료되었거나 존재하지 않는 토큰입니다."),

    // 팀
    TEAM_NOT_EXIST(HttpStatus.BAD_REQUEST, "G-001", "존재하지 않는 팀입니다."),
    TEAM_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "G-002", "해당 팀의 권한이 없습니다."),
    NOT_TEAM_LEADER(HttpStatus.BAD_REQUEST, "G-003", "팀장만 수정 권한이 있습니다.");



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