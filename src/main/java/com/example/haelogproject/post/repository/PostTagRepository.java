package com.example.haelogproject.post.repository;

import com.example.haelogproject.post.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    void deleteAllByPost_Id(Long postId);
}
