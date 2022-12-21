package com.example.haelogproject.post.dto;

import lombok.Getter;

@Getter
public class PostCreateResponseDto {
    private Long postId;
    private String nickname;

    public PostCreateResponseDto(Long postId, String nickname) {
        this.postId = postId;
        this.nickname = nickname;
    }
}
