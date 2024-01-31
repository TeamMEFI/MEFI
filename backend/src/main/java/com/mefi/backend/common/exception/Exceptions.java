package com.mefi.backend.common.exception;

public class Exceptions extends RuntimeException {

    // 에러 코드
    private ErrorCode errorCode;

    // 생성자
    public Exceptions(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}