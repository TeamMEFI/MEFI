package com.mefi.backend.api.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinReqDto {

    // 아이디(회원 이메일)
    private String email;

    // 비밀번호
    private String password;

    // 이름
    private String name;

    // 부서
    private String dept;

    // 직책
    private String position;
}