package com.example.haelogproject.comment.repository;

import com.example.haelogproject.comment.entity.Comment;
import com.example.haelogproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteAllByPost(Long postId);

    Long countByPost(Post post);
}
