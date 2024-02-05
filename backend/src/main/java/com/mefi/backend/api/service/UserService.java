package com.mefi.backend.api.service;

import com.mefi.backend.api.request.JoinReqDto;
import com.mefi.backend.api.request.UserModifyAllReqDto;
import com.mefi.backend.api.request.UserModifyPasswordReqDto;
import com.mefi.backend.api.request.UserModifyReqDto;
import com.mefi.backend.api.request.UserWithdrawReqDto;
import com.mefi.backend.api.response.MemberResDto;
import com.mefi.backend.api.response.UserModifyAllResDto;
import com.mefi.backend.db.entity.User;

import java.util.List;

public interface UserService {

    // 회원가입
    void join(JoinReqDto joinReqDto);

    // 회원탈퇴
    void withdraw(User user, UserWithdrawReqDto userWithdrawReqDto);

    // 식별 ID로 유저 조회
    User findById(Long id);

    // 검색어로 회원 조회
    List<MemberResDto> getSearchUsers(String keyword);

    // 회원 정보 전체 수정
    UserModifyAllResDto modifyUserInfoAll(Long userId, UserModifyAllReqDto userModifyAllReqDto);
    
    // 회원 정보 부분 수정
    void modifyUserInfo(Long userId, UserModifyReqDto userModifyReqDto);

    // 회원 비밀번호 수정
    void modifyUserPassword(Long id, UserModifyPasswordReqDto userModifyPasswordReqDto);
}
