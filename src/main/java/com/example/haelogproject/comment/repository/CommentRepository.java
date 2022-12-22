package com.example.haelogproject.comment.repository;

import com.example.haelogproject.comment.entity.Comment;
import com.example.haelogproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteAllByPost(Post post);

    Long countByPost(Post post);

    List<Comment> findAllByPost(Post post);
}
