package com.example.haelogproject.tag.repository;

import com.example.haelogproject.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
