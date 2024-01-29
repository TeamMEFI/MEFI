package com.mefi.backend.api.service;

import com.mefi.backend.api.request.JoinReqDto;
import com.mefi.backend.db.entity.User;
import com.mefi.backend.db.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
}
