package com.mefi.backend.api.request;

import lombok.Getter;

@Getter
public class UserPasswordRecoveryReqDto {

    // 이메일
    private String email;

    // 변경할 비밀번호
    private String modifyPassword;
}
