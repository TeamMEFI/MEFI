package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ConferenceCreateReqDto;
import com.mefi.backend.api.request.ScheduleReqDto;
import com.mefi.backend.api.response.ConferenceResDto;
import com.mefi.backend.api.response.MemberResDto;
import com.mefi.backend.api.response.NotiResponseDto;
import com.mefi.backend.common.exception.ErrorCode;
import com.mefi.backend.common.exception.Exceptions;
import com.mefi.backend.db.entity.Conference;
import com.mefi.backend.db.entity.ScheduleType;
import com.mefi.backend.db.entity.Team;
import com.mefi.backend.db.repository.ConferenceRepository;
import com.mefi.backend.db.repository.TeamRepository;
import com.mefi.backend.db.repository.TeamUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final ScheduleService scheduleService;

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

        log.info("\n리더 아이디 값 : {}", leaderId);
        log.info("\n팀 아이디 값 : {}", team.getId());

        // 팀 리더인지 확인
       teamService.checkRole(leaderId, team.getId());

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

        log.info("\n회의 생성 빌더 작업 완료--------------");

        // 개인 일정 ScheduleReqDto 생성
        ScheduleReqDto scheduleReqDto = new ScheduleReqDto(
                conferenceCreateReqDto.getCallStart(),
                conferenceCreateReqDto.getCallEnd(),
                ScheduleType.CONFERENCE,
                team.getId()+"의 회의",
                conferenceCreateReqDto.getDescription());

        // 팀원 목록 조회
        List<MemberResDto> members = teamService.getMemberList(leaderId, team.getId());

        log.info("팀원 목록 완료 후 일정 추가 시작-------------");

        // 팀원들 개인 일정에 추가
        for(MemberResDto member: members) {
            log.info("팀원 : {}", member.getId());
            scheduleService.createSchedule(member.getId(),scheduleReqDto);
        }

        // DB 저장
        conferenceRepository.save(conference);
    }

    @Override
    public List<ConferenceResDto> getConferenceHistory(Long userId, Long teamId, String start, String end) {
        // 팀 멤버인지 확인, 아니라면 예외 발생
        teamService.getMemberList(userId, teamId);

        // 문자열을 LocalDateTime으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSS");
        LocalDateTime startTime = LocalDateTime.parse(start + "000000.000", formatter);
        LocalDateTime endTime = LocalDateTime.parse(end + "235959.999", formatter);
        log.info("Start : {}, End : {}", startTime, endTime)
        ;
        // 기간 내 존재하는 회의 이력 조회
        List<Conference> histories = conferenceRepository.findAllByCallTime(startTime, endTime);

        // 엔티티를 DTO로 변환하여 리턴
        return histories.stream()
                .map(h -> new ConferenceResDto(h))
                .collect(Collectors.toList());
    }
}