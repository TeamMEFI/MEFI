package com.mefi.backend.db.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Noti {

    // 알림식별ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임하는 전략
    private Long id;

    // 메세지
    private String message;

    // 읽음여부
    private Boolean status;

    // 생성시간
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
