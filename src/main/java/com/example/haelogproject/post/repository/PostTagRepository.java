package com.example.haelogproject.post.repository;

import com.example.haelogproject.post.entity.Post;
import com.example.haelogproject.post.entity.PostTag;
import com.example.haelogproject.post.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    List<PostTag> findAllByPost(Post post);

    void deleteAllByPost(Post post);

    boolean existsByTag(Tag tag);
}
