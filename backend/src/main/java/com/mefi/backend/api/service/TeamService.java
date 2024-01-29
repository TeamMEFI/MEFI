package com.mefi.backend.api.service;

import com.mefi.backend.api.request.TeamReqDto;

public interface TeamService {

    void createTeam(Long learderId, TeamReqDto teamReqDto) throws Exception;
}
