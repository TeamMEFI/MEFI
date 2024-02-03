package com.mefi.backend.api.request;

import lombok.Getter;

@Getter
public class UserModifyReqDto {

    // 변경 항목
    private String category;

    // 변경 내용
    private String content;
}
