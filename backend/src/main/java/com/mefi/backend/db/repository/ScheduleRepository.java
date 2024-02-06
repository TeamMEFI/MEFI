package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.PrivateSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<PrivateSchedule, Long> {

}
