package com.example.haelogproject.post.repository;

import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.post.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByMemberAndTagName(Member member, String tagName);

    List<Tag> findAllByMember(Member member);
}
