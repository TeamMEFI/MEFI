package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    // 이메일로 회원 조회
    User findByEmail(String email);
}
