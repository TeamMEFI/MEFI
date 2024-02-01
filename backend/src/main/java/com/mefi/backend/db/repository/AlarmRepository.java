package com.mefi.backend.db.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface AlarmRepository {

    public void deleteByEmitterId(String emitterId);
    public SseEmitter saveEmitter(String emitterId, SseEmitter emitter);
    public Map<String, Object> getAllEventCachesbyEmitterId(String emitterId);
}
