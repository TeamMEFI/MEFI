package com.mefi.backend.api.service;

import com.mefi.backend.api.request.TeamReqDto;
import com.mefi.backend.api.response.MemberResDto;
import com.mefi.backend.api.response.TeamResDto;
import com.mefi.backend.db.entity.Team;
import com.mefi.backend.db.entity.User;
import com.mefi.backend.db.entity.UserRole;
import com.mefi.backend.db.entity.UserTeam;
import com.mefi.backend.db.repository.TeamRepository;
import com.mefi.backend.db.repository.TeamUserRepository;
import com.mefi.backend.db.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

    @Override
    @Transactional
    public void createTeam(Long leaderId, TeamReqDto teamReqDto) throws Exception {
        // 팀 생성
        Team team = Team.builder()
                .name(teamReqDto.getTeamName())
                .description(teamReqDto.getTeamDescription())
                .build();

        // 리더 추가
        User leader = userRepository.findById(leaderId).orElseThrow(() -> new IllegalArgumentException("Leader not found"));
        UserTeam teamLeader = UserTeam.builder()
                .user(leader)
                .team(team)
                .role(UserRole.LEADER)
                .build();
        team.addUserTeam(teamLeader);

        // 멤버 추가
        List<Long> memberIds = teamReqDto.getMembers();
        for (Long memberId : memberIds) {
            User member = userRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
            UserTeam teamMember = UserTeam.builder()
                    .user(member)
                    .team(team)
                    .role(UserRole.MEMBER)
                    .build();
            team.addUserTeam(teamMember);
        }

        // 팀 저장
        Team newTeam = teamRepository.save(team);
    }

    // 팀 목록 조회 서비스
    @Override
    public List<TeamResDto> getTeamList(Long userId) {

        return teamUserRepository.findTeamsByUserId(userId);
    }

    // 팀원 목록 조회
    @Override
    public List<MemberResDto> getMemberList(Long userId, Long teamId) {
        // 해당 팀에 현재 로그인한 유저가 속해있는지 확인 -> 만약 0으로 반환되면 속해있지 않음
        Long count = teamUserRepository.isMember(userId, teamId);

        // 예외처리 : 만약 해당 팀의 멤버가 아니라면... 예외 발생!!
        if(count == 0L){
            log.info("현재 로그인된 사용자는 해당 팀의 멤버가 아닙니다.");
            return null;
        }

        // 팀 구성원 목록 반환
        return teamUserRepository.getMemberList(teamId);
    }
}
