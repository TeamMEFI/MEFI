package com.mefi.backend.api.service;

import com.mefi.backend.db.repository.AlarmRepository;
import com.mefi.backend.db.repository.AlarmRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepositoryImpl alarmRepository;
    private static final long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    @Override
    public SseEmitter createSubscribe(String userId, String lastEventId) {
        // SseEmitter 객체 생성
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        // SseEmitter 추가
        String emitterId = createTimeIncludeId(userId);
        alarmRepository.saveEmitter(emitterId, emitter);

        // 오류 별로 실행할 콜백 함수 지정
        emitter.onCompletion(()-> alarmRepository.deleteByEmitterId(emitterId)); // 네트워크 오류
        emitter.onTimeout(()-> alarmRepository.deleteByEmitterId(emitterId)); // 시간 초과
//        TODO : 지울까말까
//        emitter.onError(()-> alarmRepository.deleteByEmitterId(emitterId)); // 오류

        // Event 유실을 예방하고자 클라이언트가 미수신한 Event 목록이 존재할 경우 전송
        if(!lastEventId.isEmpty()){
            // 전송되지 못한 EventCache 조회
            Map<String, Object> eventCaches = alarmRepository.getAllEventCachesbyEmitterId(emitterId);

            // 클라이언트에게 미수신한 Event 전송
            eventCaches.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0) // 미수신한 Event 조회
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue())); // 각 Event를 클라이언트에 전송
        }

        // 생성한 SSE Emitter를 클라이언트에 반환
        return emitter;
    }

    // 유저 ID와 알람 발생 시간이 포함된 Last-Event-Id 값 생성
    // Last-Event-ID 값은 클라이언트에게 유실된 데이터를 찾는데 필요한 시점을 파악하는 데 사용
    private String createTimeIncludeId(String userId){
        return userId + "_" +  System.currentTimeMillis();
    }

    // SSE 알림 전송
    private void sendToClient(SseEmitter emitter, String id, Object data){
        try{
            emitter.send(SseEmitter.event()
                                   .id(id)
                                   .name("sse")
                                   .data(data));
        }catch(IOException e){
            alarmRepository.deleteByEmitterId(id);
            throw new RuntimeException("연결 오류!");
        }
    }

}
