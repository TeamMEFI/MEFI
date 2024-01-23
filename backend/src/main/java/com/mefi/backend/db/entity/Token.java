package com.mefi.backend.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name ="token")
@Getter
public class Token {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private Long id;

    @Column(name = "refresh_token", length = 500, nullable = false)
    private String refreshToken;

    @Column(name = "user_id", nullable = false)
    private Long userId;

}
