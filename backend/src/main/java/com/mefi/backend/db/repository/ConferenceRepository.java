package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    // 회의 식별 ID로 회의 정보 조회
    Optional<Conference> findById(Long conferencId);
}