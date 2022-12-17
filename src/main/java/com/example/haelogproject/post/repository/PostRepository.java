package com.example.haelogproject.post.repository;

import com.example.haelogproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
