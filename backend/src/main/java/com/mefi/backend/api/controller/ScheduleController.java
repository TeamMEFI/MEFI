package com.mefi.backend.api.controller;

import com.mefi.backend.api.request.ScheduleReqDto;
import com.mefi.backend.api.service.ScheduleService;
import com.mefi.backend.common.auth.CustomUserDetails;
import com.mefi.backend.common.model.BaseResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "개인 일정 API", description = "각 팀원은 개인적인 일정을 추가, 수정, 삭제할 수 있다.")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @Operation(summary = "개인 일정 등록", description = "새로운 개인 일정 정보를 받아 DB에 저장한다.")
    @ApiResponse(responseCode = "201", description = "성공 시 상태 코드 201을 반환한다")
    public ResponseEntity<? extends BaseResponseBody> createSchedule(Authentication authentication, @RequestBody ScheduleReqDto scheduleReqDto){
        // 사용자 식별 ID 조회
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        log.info("User ID : {}", user.getUserId());

        // 일정 등록
        scheduleService.createSchedule(1L, scheduleReqDto);
        log.info("Schedule Summary : {}", scheduleReqDto.getSummary());

        // 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, "SUCCESS"));
    }

}
