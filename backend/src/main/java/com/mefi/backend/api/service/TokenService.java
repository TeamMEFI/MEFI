package com.mefi.backend.api.service;

import com.mefi.backend.api.response.CreateAccessTokenResDto;
import com.mefi.backend.db.entity.User;

public interface TokenService {

    // 엑세스 토큰 재발급
    CreateAccessTokenResDto createAccessToken(User user, String refreshToken);
}