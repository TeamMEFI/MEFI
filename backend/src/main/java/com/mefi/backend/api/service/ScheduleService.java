package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ScheduleReqDto;
import com.mefi.backend.api.response.ScheduleDetailResDto;
import com.mefi.backend.api.response.ScheduleResDto;

import java.util.List;

public interface ScheduleService {

    // 개인 일정 등록
    void createSchedule(Long userId, ScheduleReqDto scheduleReqDto);

    // 개인 일정 삭제
    ScheduleResDto deleteSchedule(Long userId, Long alarmId);

    List<ScheduleDetailResDto> getPrivateSchedule(Long userId, String start, String end);
}
