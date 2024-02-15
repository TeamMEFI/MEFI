package com.mefi.backend.api.request;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class UserPasswordRecoveryReqDto {

    // 이메일
    @Valid
    private String email;

    // 변경할 비밀번호
    @Valid
    private String modifyPassword;
}
