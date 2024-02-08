package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ConferenceCreateReqDto;
import com.mefi.backend.api.response.ConferenceResDto;

import java.util.List;

public interface ConferenceService {

    // 회의 생성
    void createMeeting(Long leaderId, ConferenceCreateReqDto conferenceCreateReqDto);

    // 팀 회의 이력 조회
    List<ConferenceResDto> getConferenceHistory(Long userId, Long teamId, String start, String end);
}
