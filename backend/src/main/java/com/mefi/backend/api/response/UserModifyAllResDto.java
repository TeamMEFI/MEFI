package com.mefi.backend.api.response;

import lombok.Getter;

@Getter
public class UserModifyAllResDto {

    // 이메일
    private String email;

    // 이름
    private String name;

    // 부서
    private String dept;

    // 직책
    private String position;

    // 프로필 이미지
    private String imgUrl;
}
