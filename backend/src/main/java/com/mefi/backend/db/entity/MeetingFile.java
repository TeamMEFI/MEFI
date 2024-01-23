package com.mefi.backend.db.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "file")
public class MeetingFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임하는 전략
    private Long id;

    @Column(name = "file_url")
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private MeetingFileType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conference conference;
}
