package com.mefi.backend.api.service;

import com.mefi.backend.api.request.ConferenceCreateReqDto;

public interface ConferenceService {

    // 회의 생성
    void createMeeting(Long leaderId, ConferenceCreateReqDto conferenceCreateReqDto);
}
