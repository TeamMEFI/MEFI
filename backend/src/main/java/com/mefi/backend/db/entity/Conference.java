package com.mefi.backend.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="conference")
@Getter
public class Conference {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임하는 전략
    private Long id; // 식별ID

    private Long leaderId; // 팀장ID

    private LocalDateTime callStart; //

    private LocalDateTime callEnd;

    private String thumbnailUrl;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private ConferenceStatus status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Team team;

    @OneToMany(mappedBy = "conference")
    private List<UserConference> userConferences = new ArrayList<>();

    @OneToMany(mappedBy = "conference")
    private List<MeetingFile> meetingFiles = new ArrayList<>();
}