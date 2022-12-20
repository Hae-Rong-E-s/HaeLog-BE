package com.example.haelogproject.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostInfoForUpdateDto {
    private Long postid;
    private LocalDateTime createdAt;
    private String title;
    private List<String> tags;
    private String nickname;
    private String content;

    @Builder
    public PostInfoForUpdateDto(Long postid, LocalDateTime createdAt, String title, List<String> tags, String nickname, String content) {
        this.postid = postid;
        this.createdAt = createdAt;
        this.title = title;
        this.tags = tags;
        this.nickname = nickname;
        this.content = content;
    }
}
