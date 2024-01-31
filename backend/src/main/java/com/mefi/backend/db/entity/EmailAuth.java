package com.mefi.backend.db.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "email_auth")
public class EmailAuth {

    // 식별ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임하는 전략
    private Long id;

    // 이메일
    private String email;

    // 인증번호
    private int randomNum;

    // 생성시간
    private LocalDateTime createdTime;

    @Builder
    public EmailAuth(String email, int randomNum) {
        this.email = email;
        this.randomNum = randomNum;
        this.createdTime = LocalDateTime.now();
    }

    public void updateAuthCode(int randomNum) {
        this.randomNum = randomNum;
    }
}
