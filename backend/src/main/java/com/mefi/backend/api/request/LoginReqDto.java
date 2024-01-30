package com.mefi.backend.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDto {

    // 이메일
    private String email;

    // 비밀번호
    private String password;
}
