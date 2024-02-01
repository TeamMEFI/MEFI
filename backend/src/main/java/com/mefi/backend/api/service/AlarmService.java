package com.mefi.backend.api.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {

    // SSE 커넥션 연결
    public SseEmitter createSubscribe(String userId, String lastEventId);

    // SSE 알림 생성
    // SSE 알림 전송
    // SSE 알림을 클라이언트에 전송

    // 특정 유저 알림 조회
    // 특정 알림 읽음
    // 전체 알림 읽음
    // 특정 알림 삭제
    // 전체 알림 삭제

}
