package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.PrivateSchedule;
import com.mefi.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<PrivateSchedule, Long> {

    Optional<PrivateSchedule> findById(Long scheduleId);

    List<PrivateSchedule> findByUserAndStartedTimeBetweenOrderByStartedTime(User user, LocalDateTime start, LocalDateTime end);
}
