package com.mefi.backend.api.request;

import com.mefi.backend.db.entity.ScheduleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleReqDto {

    // 시작 시간
    private LocalDateTime startedTime;

    // 종료 시간
    private LocalDateTime endTime;

    // 일정 유형
    private ScheduleType type;

    // 요약
    private String summary;

    // 상세 설명
    private String description;
}
