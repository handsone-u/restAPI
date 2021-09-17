package com.handsone.restAPI.repository;

import com.handsone.restAPI.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);

    Optional<Member> findByUserIdAndPassword(String userId, String password);
}
