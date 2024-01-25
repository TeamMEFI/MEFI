package com.mefi.backend.api.service;

import com.mefi.backend.api.request.JoinReqDto;

public interface UserService {

    // 회원가입
    void join(JoinReqDto joinReqDto);
}
