package com.mefi.backend.db.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class AlarmRepositoryImpl implements AlarmRepository{

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventChaches = new ConcurrentHashMap<>();

    public void deleteByEmitterId(String emitterId){
       emitters.remove(emitterId);
    }

    // SSE Emitter를 저장한다
    public SseEmitter saveEmitter(String emitterId, SseEmitter emitter){
        emitters.put(emitterId, emitter);
        log.info(emitters.toString());
        return emitter;
    }

    // EmitterId에 해당하는 캐시를 모두 반환한다
    public Map<String, Object> getAllEventCachesbyEmitterId(String emitterId){
        eventChaches.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(emitterId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return eventChaches;
    }
}
