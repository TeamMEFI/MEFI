package com.mefi.backend.api.service;

import com.mefi.backend.api.request.JoinReqDto;
import com.mefi.backend.api.request.UserModifyPasswordReqDto;
import com.mefi.backend.api.request.UserModifyReqDto;
import com.mefi.backend.api.response.MemberResDto;
import com.mefi.backend.common.exception.ErrorCode;
import com.mefi.backend.common.exception.Exceptions;
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

    // 회원 정보 수정
    @Override
    @Transactional
    public void modifyUserInfo(Long id, UserModifyReqDto userModifyReqDto) {

        // 유저 조회
        if (!userRepository.findById(id).isPresent())
            throw new Exceptions(ErrorCode.USER_NOT_EXIST);

        User user = userRepository.findById(id).get();

        // 항목에 맞게 수정
        if("name".equals(userModifyReqDto.getCategory()))
            user.updateName(userModifyReqDto.getContent());

        else if("password".equals(userModifyReqDto.getCategory())) {

            // 이전 비밀번호와 동일한 경우
            if(user.getPassword().equals(bCryptPasswordEncoder.encode(userModifyReqDto.getContent())))
                throw new Exceptions(ErrorCode.SAME_AS_BEFORE);

            user.updatePassword(bCryptPasswordEncoder.encode(userModifyReqDto.getContent()));
        }


        else if("dept".equals(userModifyReqDto.getCategory()))
            user.updateDept(userModifyReqDto.getContent());

        else if("position".equals(userModifyReqDto.getCategory()))
            user.updatePosition(userModifyReqDto.getContent());

        else if("imgUrl".equals(userModifyReqDto.getCategory()))
            user.updateImgUrl(userModifyReqDto.getContent());

        // 항목이 존재하지 않는 경우
        else throw new Exceptions(ErrorCode.CATEGORY_NOT_EXIST);
    }

    // 회원 비밀번호 수정
    @Override
    @Transactional
    public void modifyUserPassword(UserModifyPasswordReqDto userModifyPasswordReqDto) {

        // 유저 조회
        if(!userRepository.findByEmail(userModifyPasswordReqDto.getEmail()).isPresent())
            throw new Exceptions(ErrorCode.USER_NOT_EXIST);

        User user = userRepository.findByEmail(userModifyPasswordReqDto.getEmail()).get();

        // 이전 비밀번호와 동일한 경우
        if(user.getPassword().equals(bCryptPasswordEncoder.encode(userModifyPasswordReqDto.getPassword())))
            throw new Exceptions(ErrorCode.SAME_AS_BEFORE);
        
        user.updatePassword(bCryptPasswordEncoder.encode(userModifyPasswordReqDto.getPassword()));
    }
}
