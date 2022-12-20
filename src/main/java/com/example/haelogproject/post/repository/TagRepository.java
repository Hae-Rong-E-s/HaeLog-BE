package com.example.haelogproject.post.repository;

import com.example.haelogproject.post.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagNameAndMember_Id(String tagName, Long member_id);

    List<Tag> findAllByMember_Id(Long memberId);
}
