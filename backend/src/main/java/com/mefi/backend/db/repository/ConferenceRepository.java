package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

}
