package com.mefi.backend.db.repository;

import com.mefi.backend.db.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// 스프링 데이터 리포지토리에 사용자 정의 인터페이스 상속
public interface TeamUserRepository extends JpaRepository<UserTeam, Long>, TeamUserRepositoryCustom {

    @Query("SELECT ut FROM UserTeam ut WHERE ut.user.id = :userId AND ut.team.id = :teamId")
    UserTeam findByUserIdAndTeamId(Long userId, Long teamId);
}
