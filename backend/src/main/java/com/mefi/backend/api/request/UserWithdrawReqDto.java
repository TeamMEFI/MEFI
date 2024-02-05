package com.mefi.backend.api.request;

import lombok.Getter;

@Getter
public class UserWithdrawReqDto {

    // 본인 인증을 위한 현재 비밀번호
    private String currentPassword;

}