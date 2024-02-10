package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ConferenceCreateReqDto;
import com.mefi.backend.api.response.ConferenceResDto;
import java.util.List;
import com.mefi.backend.api.response.ConferenceDetailResDto;

public interface ConferenceService {

    // 회의 생성
    Long createMeeting(Long leaderId, ConferenceCreateReqDto conferenceCreateReqDto);

    // 팀 회의 이력 조회
    List<ConferenceResDto> getConferenceHistory(Long userId, Long teamId, String start, String end);

    // 회의 상세 조회
    ConferenceDetailResDto detailMeeting(Long userId, Long conferenceId);

    // 회의 취소
    void cancelMeeting(Long userId, Long conferenceId);
}
