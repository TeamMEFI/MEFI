package com.mefi.backend.db.entity;

import jakarta.persistence.*;

/**
 * 유저-팀 연관테이블
 */
@Entity
@Table(name = "user_team")
public class UserTeam {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

}
