package com.mefi.backend.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyEmailReqDto {

    // 이메일 전송 아이디
    private String email;
}
