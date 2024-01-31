package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository <EmailAuth, Long> {
}
