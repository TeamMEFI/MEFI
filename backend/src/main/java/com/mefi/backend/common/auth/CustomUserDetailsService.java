package com.mefi.backend.common.auth;

import com.mefi.backend.db.entity.User;
import com.mefi.backend.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 이메일을 기반으로 사용자 정보를 조회하고, AutneticationManager 검증을 위해 UserDetails 객체로 반환
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 회원 조회
        User user = userRepository.findByEmail(email);

        // 회원이 존재하는 경우
        if(user != null) {

            // UserDetails 담아 반환
            return new CustomUserDetails(user);
        }

        // 회원이 없는 경우
        return null;
    }
}