package com.example.haelogproject.post.repository;

import com.example.haelogproject.post.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
