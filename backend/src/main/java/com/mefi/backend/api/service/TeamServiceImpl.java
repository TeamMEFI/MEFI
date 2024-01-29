package com.mefi.backend.api.service;

import com.mefi.backend.api.request.TeamReqDto;
import com.mefi.backend.db.entity.Team;
import com.mefi.backend.db.entity.User;
import com.mefi.backend.db.entity.UserRole;
import com.mefi.backend.db.entity.UserTeam;
import com.mefi.backend.db.repository.TeamRepository;
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
//    private final

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
}
