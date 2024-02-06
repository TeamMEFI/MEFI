package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ScheduleReqDto;
import com.mefi.backend.api.response.ScheduleResDto;
import com.mefi.backend.common.exception.ErrorCode;
import com.mefi.backend.common.exception.Exceptions;
import com.mefi.backend.db.entity.PrivateSchedule;
import com.mefi.backend.db.entity.User;
import com.mefi.backend.db.repository.ScheduleRepository;
import com.mefi.backend.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements  ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public void createSchedule(Long userId, ScheduleReqDto scheduleReqDto) {
        // 유저 조회, 해당 회원이 존재하지 않으면 예외 처리
        User user = userRepository.findById(userId).orElseThrow(()->new Exceptions(ErrorCode.MEMBER_NOT_EXIST));

        // 일정 등록
        PrivateSchedule privateSchedule = PrivateSchedule.builder()
                .user(user)
                .startedTime(scheduleReqDto.getStartedTime())
                .endTime(scheduleReqDto.getEndTime())
                .summary(scheduleReqDto.getSummary())
                .description(scheduleReqDto.getDescription())
                .type(scheduleReqDto.getType())
                .build();

        // 일정 등록
        scheduleRepository.save(privateSchedule);
    }

    @Override
    @Transactional
    public ScheduleResDto deleteSchedule(Long userId, Long alarmId) {
        // 삭제하려는 일정 조회, 존재하지 않는다면 예외 발생
        PrivateSchedule schedule = scheduleRepository.findById(alarmId).orElseThrow(()->new Exceptions(ErrorCode.SCHEDULE_NOT_EXIST));

        // 일정을 등록한 유저가 아니라면 예외 발생
        if(userId != schedule.getUser().getId()){
            throw new Exceptions(ErrorCode.SCHEDULE_ACCESS_DENIED);
        }

        // 일정 삭제
        scheduleRepository.delete(schedule);
        return new ScheduleResDto(schedule);
    }
}
