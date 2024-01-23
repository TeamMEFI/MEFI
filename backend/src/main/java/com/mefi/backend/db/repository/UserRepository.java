package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
