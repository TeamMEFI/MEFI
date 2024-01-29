package com.mefi.backend.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "private_schedule")
@Getter
public class PrivateSchedule {
    // 일정식별ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임하는 전략
    private Long id;

    // 시작시간
    private LocalDateTime startedTime;

    // 종료시간
    private LocalDateTime endTime;

    // 개인일정
    @Enumerated(EnumType.STRING)
    private ScheduleType type;

    // 유저와 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
