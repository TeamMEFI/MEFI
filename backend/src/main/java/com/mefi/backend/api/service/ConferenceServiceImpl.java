package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ConferenceCreateReqDto;
import com.mefi.backend.api.request.ScheduleReqDto;
import com.mefi.backend.api.response.ConferenceResDto;
import com.mefi.backend.api.response.ConferenceDetailResDto;
import com.mefi.backend.api.response.MemberResDto;
import com.mefi.backend.common.exception.ErrorCode;
import com.mefi.backend.common.exception.Exceptions;
import com.mefi.backend.db.entity.*;
import com.mefi.backend.db.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final ScheduleService scheduleService;
    private final TeamUserRepository teamUserRepository;
    private final FileRepository fileRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final NotiService notiService;

    // 회의 생성
    @Override
    @Transactional
    public Long createMeeting(Long leaderId, ConferenceCreateReqDto conferenceCreateReqDto) {

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

        log.info("\n팀원 목록 완료 후 일정 추가 시작-------------");

        // 팀원들 개인 일정에 추가
        for(MemberResDto member: members) {
            log.info("\n팀원 : {}", member.getId());
            scheduleService.createSchedule(member.getId(),scheduleReqDto);
        }

        // DB 저장
        conferenceRepository.save(conference);

        // 팀원 모두에게 알림 전송
        String sender = team.getName();
        String message =  makeMessage(sender, conference.getTitle(),1);
        notiService.sendNotiForTeam(team.getId(), sender, message);

        return conference.getId();
    }

    @Override
    public List<ConferenceResDto> getConferenceHistory(Long userId, Long teamId, String start, String end) {
        // 팀 멤버인지 확인, 아니라면 예외 발생
        teamService.getMemberList(userId, teamId);

        // 문자열을 LocalDateTime으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSS");
        LocalDateTime startTime = LocalDateTime.parse(start + "000000.000", formatter);
        LocalDateTime endTime = LocalDateTime.parse(end + "235959.999", formatter);
        log.info("Start : {}, End : {}", startTime, endTime);

        // 기간 내 존재하는 회의 이력 조회, DTO를 직접 조회한다
        List<ConferenceResDto> histories = conferenceRepository.findAllByCallTime(teamId, startTime, endTime);
        return histories;
    }

    // 회의 상세 조회
    @Override
    public ConferenceDetailResDto detailMeeting(Long userId, Long conferenceId) {

        // 회의 진행하는 팀 확인
        if(!conferenceRepository.findById(conferenceId).isPresent())
            throw new Exceptions(ErrorCode.CONFERENCE_NOT_EXIST);

        log.info("\n회의 진행하는 팀 확인 : OK");

        Conference conference = conferenceRepository.findById(conferenceId).get();

        log.info("\n회의 조회 : OK");

        // 해당 팀의 팀원인지 확인
        if(teamUserRepository.isMember(userId, conference.getTeam().getId())==0)
            throw new Exceptions(ErrorCode.NOT_TEAM_MEMBER);

        log.info("\n회의 팀의 팀원인지 확인 : OK");

        // 해당 회의관련 파일 메타 데이터 가져오기
        List<MeetingFile> meetingFiles = fileRepository.findByConferenceId(conferenceId);

        log.info("\n파일 메타 데이터 가져오기 : OK");
        log.info("\n파일 개수 : {}", meetingFiles.size());

        // 파일 리스트를 담을 리스트
        List<String> conferenceFileList = new ArrayList<>();
        
        // 파일 리스트 출력
        for(MeetingFile meetingFile: meetingFiles)
            conferenceFileList.add(meetingFile.getFileName());

        // 회의 상세 조회 Dto에 담기
        ConferenceDetailResDto conferenceDetailResDto = new ConferenceDetailResDto(
                conference.getTitle(),conference.getDescription(),
                conference.getCallStart(),conference.getCallEnd(),
                conference.getThumbnailUrl(), conferenceFileList);

        return conferenceDetailResDto;
    }

    // 회의 취소
    @Override
    @Transactional
    public void cancelMeeting(Long leaderId, Long conferenceId) {

        // 회의 존재 여부 확인
        if(!conferenceRepository.findById(conferenceId).isPresent())
            throw new Exceptions(ErrorCode.CONFERENCE_NOT_EXIST);

        Conference conference = conferenceRepository.findById(conferenceId).get();

        log.info("\n회의 조회 : OK");

        // 팀 존재 여부 확인
        if(!teamRepository.findById(conference.getTeam().getId()).isPresent())
            throw new Exceptions(ErrorCode.TEAM_NOT_EXIST);

        Team team = teamRepository.findById(conference.getTeam().getId()).get();

        // 해당 팀의 리더인지 확인
        teamService.checkRole(leaderId, team.getId());
        
        log.info("\n팀 존재 여부 & 팀의 리더인지 확인 : OK");

        // 회의 상태 변경
        conference.cancelConferenceStatus();

        log.info("\n회의 상태 변경 완료 : {}", conference.getStatus());

        // 팀원들 개인 일정에서 회의 삭제
        List<MemberResDto> members = teamService.getMemberList(leaderId, team.getId());

        // 팀원 모두에게 알림 전송
        String sender = team.getName();
        String message =  makeMessage(sender, conference.getTitle(),-1);
        notiService.sendNotiForTeam(team.getId(), sender, message);

        // 팀원 순회
        for(MemberResDto member: members) {

            // 유저 조회
            if(!userRepository.findById(member.getId()).isPresent())
                throw new Exceptions(ErrorCode.USER_NOT_EXIST);

            User user = userRepository.findById(member.getId()).get();

            // 해당 시간 개인 일정 조회
            List<PrivateSchedule> privateSchedules =
                    scheduleRepository.findByUserAndStartedTimeBetweenOrderByStartedTime(
                            user,conference.getCallStart(),conference.getCallEnd());
            scheduleService.deleteSchedule(user.getId(),privateSchedules.get(0).getId());

            log.info("\n 팀원 : {}, {}", user.getId(),user.getName());
            log.info("\n 개인 일정 삭제 완료 : OK");
        }
    }

    // 회의 종료
    @Override
    @Transactional
    public void doneMeeting(Long leaderId, Long conferenceId) {

        // 회의 존재 여부 확인
        if(!conferenceRepository.findById(conferenceId).isPresent())
            throw new Exceptions(ErrorCode.CONFERENCE_NOT_EXIST);

        Conference conference = conferenceRepository.findById(conferenceId).get();

        log.info("\n회의 조회 : OK");

        // 팀 존재 여부 확인
        if(!teamRepository.findById(conference.getTeam().getId()).isPresent())
            throw new Exceptions(ErrorCode.TEAM_NOT_EXIST);

        Team team = teamRepository.findById(conference.getTeam().getId()).get();

        // 해당 팀의 리더인지 확인
        teamService.checkRole(leaderId, team.getId());

        log.info("\n팀 존재 여부 & 팀의 리더인지 확인 : OK");

        // 회의 상태 변경
        conference.doneConferenceStatus();

        log.info("\n회의 상태 변경 완료 : {}", conference.getStatus());
    }

    public String makeMessage(String sender, String conferenceName, int type){
        if(type==1)
            return String.format("팀[%s]에 회의 %s가 예약되었습니다.", sender, conferenceName);
        return String.format("팀[%s]의 회의 %s가 취소되었습니다.", sender, conferenceName);
    }
}