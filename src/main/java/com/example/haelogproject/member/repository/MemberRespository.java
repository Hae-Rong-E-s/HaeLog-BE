package com.example.haelogproject.member.repository;

import com.example.haelogproject.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRespository extends JpaRepository<Member, Long> {
}
