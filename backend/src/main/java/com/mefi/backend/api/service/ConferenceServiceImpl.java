package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ConferenceCreateReqDto;
import com.mefi.backend.common.exception.ErrorCode;
import com.mefi.backend.common.exception.Exceptions;
import com.mefi.backend.db.entity.Conference;
import com.mefi.backend.db.entity.Team;
import com.mefi.backend.db.repository.ConferenceRepository;
import com.mefi.backend.db.repository.TeamRepository;
import com.mefi.backend.db.repository.TeamUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;

    // 회의 생성
    @Override
    @Transactional
    public void createMeeting(Long leaderId, ConferenceCreateReqDto conferenceCreateReqDto) {

        // 팀 존재 여부 확인
        if(!teamRepository.findById(conferenceCreateReqDto.getTeamId()).isPresent()) {
            teamRepository.findById(conferenceCreateReqDto.getTeamId())
                    .orElseThrow(() -> new Exceptions(ErrorCode.TEAM_NOT_EXIST));
        }

        // 팀 조회
        Team team = teamRepository.findById(conferenceCreateReqDto.getTeamId()).get();
        
        // 팀 리더인지 확인
        if(!teamService.checkRole(leaderId,team.getId()))
            throw new Exceptions(ErrorCode.NOT_TEAM_LEADER);

        // 회의 정보 입력
        Conference conference = Conference.builder()
                .leaderId(leaderId)
                .title(conferenceCreateReqDto.getTitle())
                .description(conferenceCreateReqDto.getDescription())
                .callStart(conferenceCreateReqDto.getCallStart())
                .callEnd(conferenceCreateReqDto.getCallEnd())
                .team(team)
                .thumbnailUrl(conferenceCreateReqDto.getThumbnailUrl())
                .build();

        // DB 저장
        conferenceRepository.save(conference);
    }
}