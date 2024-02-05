package com.mefi.backend.api.request;

import lombok.Getter;

@Getter
public class UserModifyPasswordReqDto {

    // 현재 비밀번호
    private String currentPassword;

    // 변경 비밀번호
    private String modifyPassword;
}