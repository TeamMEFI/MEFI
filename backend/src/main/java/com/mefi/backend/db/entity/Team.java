package com.mefi.backend.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
public class Team {
    // 식별ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임하는 전략
    private Long id;
    
    // 팀명
    private String name;

    // 팀소개
    private String description;

    // 생성시간
    private LocalDateTime createdTime;


    @OneToMany(mappedBy = "team")
    private List<UserTeam> userTeams = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Conference> conferences = new ArrayList<>();

}
