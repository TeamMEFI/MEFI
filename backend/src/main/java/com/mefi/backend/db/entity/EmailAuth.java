package com.mefi.backend.db.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_auth")
public class EmailAuth {
    // 식별ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임하는 전략
    private Long id;

    // 인증번호
    private int randomNum;

    // 생성시간
    private LocalDateTime createdTime;

}
