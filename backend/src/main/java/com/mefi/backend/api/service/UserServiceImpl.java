package com.mefi.backend.api.service;

import com.mefi.backend.api.request.JoinReqDto;
import com.mefi.backend.api.response.MemberResDto;
import com.mefi.backend.db.entity.Token;
import com.mefi.backend.db.entity.User;
import com.mefi.backend.db.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    // 회원가입
    @Override
    @Transactional
    public void join(JoinReqDto joinReqDto) {
        
        // 가입 정보 입력
        User user = User.builder()
                .email(joinReqDto.getEmail())
                .password(bCryptPasswordEncoder.encode(joinReqDto.getPassword()))
                .name(joinReqDto.getName())
                .position(joinReqDto.getPosition())
                .dept(joinReqDto.getDept())
                .build();

        // DB 저장
        userRepository.save(user);
    }

    // 회원탈퇴
    @Override
    @Transactional
    public void withdraw(User user) {

        // 유저 토큰 삭제
        Token token = tokenService.findByUserId(user.getId());
        tokenService.deleteRefreshToken(token);

        // 유저 삭제
        userRepository.delete(user);
    }

    // 식별 ID로 유저 조회
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
    }

    // 검색어로 유저 조회
    @Override
    public List<MemberResDto> getSearchUsers(String keyword) {
        return userRepository.findByKeyWord(keyword);
    }
}
