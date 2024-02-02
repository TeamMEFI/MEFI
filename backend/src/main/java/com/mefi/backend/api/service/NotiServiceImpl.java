package com.mefi.backend.api.service;

import com.mefi.backend.api.response.MemberResDto;
import com.mefi.backend.api.response.NotiResponseDto;
import com.mefi.backend.db.entity.Noti;
import com.mefi.backend.db.entity.User;
import com.mefi.backend.db.repository.NotiRepository;
import com.mefi.backend.db.repository.TeamUserRepository;
import com.mefi.backend.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotiServiceImpl implements NotiService{

    private final NotiRepository notiRepository;
    private final TeamUserRepository teamUserRepository;
    private final UserRepository userRepository;
    private final Long DEFAULT_TIMEOUT = Long.MAX_VALUE; // SSE 최대 연결 시간

    @Override
    public SseEmitter createSseConnection(Long userId, String lastEventId) {
        // SSE Emitter ID 생성
        String emitterId = userId + "_" + System.currentTimeMillis();

        // SSE Emitter 객체 생성
        SseEmitter sseEmitter = notiRepository.saveEmitter(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // SSE 콜백 함수 지정
        sseEmitter.onCompletion(()->notiRepository.deleteEmitterById(emitterId));  // 비동기 처리 완료
        sseEmitter.onTimeout(()->notiRepository.deleteEmitterById(emitterId));  // 타임 아웃
        sseEmitter.onError((e)->notiRepository.deleteEmitterById(emitterId)); // 오류

        // 503 에러 방지를 위해 더미 이벤트 전송
        String eventId = userId + "_" + System.currentTimeMillis();
        sendNoti(sseEmitter, eventId, emitterId, Noti.builder().message("[Created] Event Stream : userID="+userId).status(false).build());

        // 네트워크 오류 등으로 인한 미수신 알림이 있다면 클라이언트에 전송
        if(!lastEventId.isEmpty()){
            Map<String, Noti> events = notiRepository.findEventCachesByUserId(String.valueOf(userId));
            events.entrySet().stream()
                    .filter(entry->lastEventId.compareTo(entry.getKey())<0) // 미수신 알림 판별
                    .forEach(entry->sendNoti(sseEmitter, entry.getKey(), emitterId, entry.getValue())); // 알림 전송
        }

        // SSE Emitter 객체 반환
        return sseEmitter;
    }

    @Override
    public void sendNoti(SseEmitter emitter, String eventId, String emitterId, Noti noti) {
        try{
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(new NotiResponseDto(noti))
                    .build());
        }catch(IOException e){
            e.printStackTrace();
            log.info("알림 전송 중 오류가 발생하였습니다");
            notiRepository.deleteEmitterById(emitterId);
        }
    }

    @Override
    @Transactional
    public void sendNotiForUser(Long userId, String message) {
        // Event ID 생성
        String eventId = makeTimeIncludeEventId(String.valueOf(userId));

        // 해당 유저 조회
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("Member not found"));

        // DB 저장
        Noti noti = Noti.builder()
                        .user(user)
                        .message(message)
                        .createdTime(LocalDateTime.now())
                        .status(false)
                        .build();
        notiRepository.createNoti(noti);

        // 로그인한 특정 사용자에 대한 모든 SSE Emitter 조회 (여러 탭, 디바이스 고려)
        Map<String, SseEmitter> emitters = notiRepository.findSseEmittersById(String.valueOf(userId));

        // 모든 SSE Emitter에 대해 알림 전송
        emitters.forEach((key, emitter) -> {
            // 이벤트 캐시 저장
            notiRepository.saveEventCache(key, noti);
            // 알림 전송
            sendNoti(emitter, eventId, key, noti);
        });


    }

    @Override
    public void sendNotiForTeam(Long teamId, String message) {
        // 팀원 목록 조회
        List<MemberResDto> users = teamUserRepository.getMemberList(teamId);

        // 각 팀원에 대해 알림 전송
        for(MemberResDto user : users){
            sendNotiForUser(user.getId(), message);
        }
    }

    @Override
    public List<NotiResponseDto> getNotis(Long userId) {
        // 특정 사용자의 알림 전체 조회
        List<Noti> notis = notiRepository.findNotiByUserId(userId);
        log.info("읽어온 Data 개수 : {}", notis.size());
        // 엔티티를 DTO로 변환
        List<NotiResponseDto> result = notis.stream()
                .map(n -> new NotiResponseDto(n))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    @Transactional
    public NotiResponseDto readNoti(Long alarmId) {
        // 특정 알림 읽음 처리
        Noti noti = notiRepository.findNotiById(alarmId);
        noti.read();
        NotiResponseDto notiResponseDto = new NotiResponseDto(noti);
        return notiResponseDto;
    }

    // 전체 알림 읽음 처리 메소드
    @Transactional
    public int readNotiAll(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return notiRepository.readNotiAll(user);
    }

    private String makeTimeIncludeEventId(String userId){
        return userId + "_" + System.currentTimeMillis();
    }

}
