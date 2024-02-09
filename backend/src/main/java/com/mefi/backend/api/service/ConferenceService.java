package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ConferenceCreateReqDto;
import com.mefi.backend.api.response.ConferenceDetailResDto;

public interface ConferenceService {

    // 회의 생성
    void createMeeting(Long leaderId, ConferenceCreateReqDto conferenceCreateReqDto);

    // 회의 상세 조회
    ConferenceDetailResDto detailMeeting(Long userId, Long conferenceId);
}
