package com.example.haelogproject.comment.repository;

import com.example.haelogproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteAllByPost_Id(Long postId);
}
