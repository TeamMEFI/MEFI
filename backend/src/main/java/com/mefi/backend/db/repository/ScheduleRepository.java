package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.PrivateSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<PrivateSchedule, Long> {

    Optional<PrivateSchedule> findById(Long scheduleId);
}
