package com.mefi.backend.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 유저-회의 연관테이블
 */
@Entity
@Table(name = "user_conference")
@Getter
public class UserConference {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id")
    private Conference conference;
}
