package com.mefi.backend.api.service;

import com.mefi.backend.api.request.TeamReqDto;
import com.mefi.backend.api.response.TeamResDto;

import java.util.List;

public interface TeamService {

    void createTeam(Long learderId, TeamReqDto teamReqDto) throws Exception;

    // 팀 목록 조회
    List<TeamResDto> getTeamList(Long userId);
}
