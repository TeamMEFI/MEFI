package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ScheduleReqDto;

public interface ScheduleService {
    void createSchedule(Long userId, ScheduleReqDto scheduleReqDto);
}
