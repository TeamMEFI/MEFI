package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.Noti;
import com.mefi.backend.db.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NotiRepositoryImpl implements NotiRepository {

    private final ConcurrentHashMap<String, SseEmitter> emitters = new ConcurrentHashMap<>(); // 실시간 알림을 전송하기 위해 서버에서 SSE Emitter 관리
    private final ConcurrentHashMap<String, Noti> eventCaches = new ConcurrentHashMap<>(); // 네트워크 오류 등으로 인한 알림 유실을 위한 캐시

    private final EntityManager em;

    @Override
    public SseEmitter saveEmitter(String emitterId, SseEmitter emitter) {
        emitters.put(emitterId, emitter);
        return emitter;
    }

    @Override
    public void deleteEmitterById(String emitterId) {
        emitters.remove(emitterId);
        log.info("Removed Emitter ID : {}", emitterId);
    }

    @Override
    public void deleteEmittersByUserId(String userId) {
        emitters.forEach(
                (key, emitter)->{
                    if(key.startsWith(userId)){
                        emitters.remove(key);
                    }
                }
        );
    }

    @Override
    public void deleteEventCacheByUserId(String userId) {
    }

    @Override
    public Map<String, SseEmitter> findSseEmittersById(String userId) {
        return emitters.entrySet().stream()
                .filter(entry->entry.getKey().startsWith(userId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Noti> findEventCachesByUserId(String userId) {
        return eventCaches.entrySet().stream()
                .filter(entry->entry.getKey().startsWith(userId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<Noti> findNotiByUserId(Long userId) {
        return em.createQuery("select n from Noti n inner join n.user u on u.id=:userId where n.status=false")
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Noti findNotiById(Long alarmId){
        return em.find(Noti.class, alarmId);
    }

    @Override
    public void createNoti(Noti noti){
        em.persist(noti);
    }

    @Override
    public void saveEventCache(String key, Noti noti){
        eventCaches.put(key, noti);
    }

    public int readNotiAll(User user){
        String qlString =
                "update Noti n " +
                "set n.status=true " +
                "where n.status=false " +
                "and n.user=:user";


        // 벌크 연산 수행
        int count = em.createQuery(qlString)
                    .setParameter("user", user)
                    .executeUpdate();

        // 데이터 조회 시, 변경되지 않은 값을 조회하는 것을 방지하고자 영속성 컨텍스트 초기화
        em.clear();

        return count;
    }
}
