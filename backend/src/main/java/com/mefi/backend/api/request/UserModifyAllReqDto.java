package com.mefi.backend.api.request;

import lombok.Getter;

@Getter
public class UserModifyAllReqDto {

    // 이름
    private String name;

    // 부서
    private String dept;

    // 직책
    private String position;
}
