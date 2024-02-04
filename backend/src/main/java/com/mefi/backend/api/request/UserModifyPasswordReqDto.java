package com.mefi.backend.api.request;

import lombok.Getter;

@Getter
public class UserModifyPasswordReqDto {

    // 이메일
    private String email;

    // 변경 비밀번호
    private String password;
}
