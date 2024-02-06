package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ScheduleReqDto;
import com.mefi.backend.api.response.ScheduleResDto;

public interface ScheduleService {

    // 개인 일정 등록
    void createSchedule(Long userId, ScheduleReqDto scheduleReqDto);

    // 개인 일정 삭제
    ScheduleResDto deleteSchedule(Long userId, Long alarmId);
}
