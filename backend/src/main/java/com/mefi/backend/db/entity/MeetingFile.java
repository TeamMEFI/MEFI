package com.mefi.backend.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file")
public class MeetingFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임하는 전략
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_url", length = 1000)
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private MeetingFileType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @Builder
    public MeetingFile(String fileName, String fileUrl, MeetingFileType type, Conference conference){
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.type = type;
        this.conference = conference;
    }
}
