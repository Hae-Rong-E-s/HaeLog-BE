package com.example.haelogproject.member.repository;

import com.example.haelogproject.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByLoginId(String loginId);

    Member findByMemberId(Long memberId);
}
