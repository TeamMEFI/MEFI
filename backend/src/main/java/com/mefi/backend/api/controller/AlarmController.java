package com.mefi.backend.api.controller;

import com.mefi.backend.api.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
@Tag(name="알람 API", description = "SSE를 포함한 알림 관리를 위한 API")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "SSE 커넥션 생성", description = "SSE 알림 전송을 위해 클라이언트와 연결을 생성합니다")
    public ResponseEntity<SseEmitter> subscribe(
            @RequestParam(name = "userId") @Parameter(name = "userId", description = "SSE 연결 ID값 생성에 사용되는 유저ID") String userId,
            @RequestParam(name = "lastEventId") @Parameter(name = "lastEventId", description = "유실된 알림 데이터를 찾기 위한 lastEventId") String lastEventId
    ){
        SseEmitter emitter = alarmService.createSubscribe(userId, lastEventId);
//        emitter.send(SseEmitter.event()
//                .id(String.valueOf(LocalDateTime.now()))
//                .name("sse")
//                .data("Hello world"));
        return ResponseEntity.status(HttpStatus.CREATED).body(emitter);
    }
}
