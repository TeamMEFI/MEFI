package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ScheduleReqDto;

public interface ScheduleService {

    // 개인 일정 등록
    void createSchedule(Long userId, ScheduleReqDto scheduleReqDto);
}
