package com.mefi.backend.api.service;

import com.mefi.backend.api.request.JoinReqDto;
import com.mefi.backend.db.entity.User;

public interface UserService {

    // 회원가입
    void join(JoinReqDto joinReqDto);

    // 회원탈퇴
    void withdraw(User user);

    // 식별 ID로 유저 조회
    User findById(Long id);
}
