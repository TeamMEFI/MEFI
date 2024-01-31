package com.mefi.backend.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyCodeReqDto {

    // 인증 이메일
    private String email;

    // 인증 번호
    private String authCode;
}
