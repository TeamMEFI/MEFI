package com.mefi.backend.db.repository;

import com.mefi.backend.api.response.FileListResponseDto;
import com.mefi.backend.db.entity.Conference;
import com.mefi.backend.db.entity.MeetingFile;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final EntityManager em;

    // 로컬 DB에 파일에 대한 메타 데이터 정보 저장
    public void upload(MeetingFile meetingFile){
        em.persist(meetingFile);
    }

    // 파일 명으로 메타 데이터 조회
    public MeetingFile findByName(String fileName){
        return (MeetingFile) em.createQuery("select m from MeetingFile m where m.fileName=:fileName")
                .setParameter("fileName", fileName)
                .getSingleResult();
    }

    // 특정 회의와 관련된 파일들의 메타 데이터 조회
    public List<MeetingFile> getFileList(Long conferenceId){
        return em.createQuery("select m from MeetingFile m join m.conference c where c.id=:conferenceId")
                .setParameter("conferenceId", conferenceId)
                .getResultList();
    }

    // TODO : CONFERENCE API 완성되면 사라질 메소드
    public Conference findById(Long conferenceId){
        return em.find(Conference.class, conferenceId);
    }

    // 파일에 대한 메타 데이터 정보 삭제
    public void deleteFile(MeetingFile file){
        em.remove(file);
    }
}
