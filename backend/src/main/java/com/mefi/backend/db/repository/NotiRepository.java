package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.Noti;
import com.mefi.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

public interface NotiRepository {

    // SSE Emitter 저장
    public SseEmitter saveEmitter(String emitterId, SseEmitter emitter);

    // SSE Emitter 삭제
    public void deleteEmitterById(String emitterId);

    // 특정 유저의 SSE Emitter 모두 삭제
    public void deleteEmittersByUserId(String userId);

    // 특정 유저의 EventCache 모두 삭제
    public void deleteEventCacheByUserId(String userId);

    // 특정 유저의 SSE Emitter 모두 조회
    public Map<String, SseEmitter> findSseEmittersById(String userId);

    // 특정 유저의 EventCache 모두 조회
    public Map<String, Noti> findEventCachesByUserId(String userId);

    // 특정 유저의 알람 전체 조회
    List<Noti> findNotiByUserId(Long userId);
    
    // 특정 알람 조회
    Noti findNotiById(Long alarmId);

    // 신규 알람 저장
    void createNoti(Noti noti);

    // 신규 EventCache 저장
    void saveEventCache(String key, Noti noti);

    // 전체 알람 읽음 처리
    int readNotiAll(@Param("user") User user);
}
