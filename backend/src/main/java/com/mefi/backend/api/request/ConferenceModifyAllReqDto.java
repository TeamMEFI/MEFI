package com.mefi.backend.api.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ConferenceModifyAllReqDto {

    // 회의 시작 시간
    private LocalDateTime callStart;

    // 회의 종료 시간
    private LocalDateTime callEnd;
    
    // 회의 제목
    private String title;
    
    // 회의 설명
    private String description;
}
