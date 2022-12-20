package com.example.haelogproject.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostSimpleResponseDto {
    private Long postid;
    private LocalDateTime createdAt;
    private String title;
    private List<String> tags = new ArrayList<>();
    private String contentSummary;
    private String nickname;
    private Long commentCount;

    @Builder
    public PostSimpleResponseDto(Long postid, LocalDateTime createdAt, String title, List<String> tags, String contentSummary, String nickname, Long commentCount) {
        this.postid = postid;
        this.createdAt = createdAt;
        this.title = title;
        this.tags = tags;
        this.contentSummary = contentSummary;
        this.nickname = nickname;
        this.commentCount = commentCount;
    }
}
