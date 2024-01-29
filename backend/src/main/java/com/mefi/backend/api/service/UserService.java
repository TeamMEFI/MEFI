package com.mefi.backend.api.service;

import com.mefi.backend.api.request.JoinReqDto;
import com.mefi.backend.db.entity.User;

import java.util.Optional;

public interface UserService {

    // 회원가입
    void join(JoinReqDto joinReqDto);

    // 식별 ID로 유저 조회
    Optional<User> findById(Long id);
}
