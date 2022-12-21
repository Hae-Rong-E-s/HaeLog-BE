package com.example.haelogproject.post.dto;

import lombok.Getter;

@Getter
public class PostCreateResponseDto {
    private Long postid;
    private String nickname;

    public PostCreateResponseDto(Long postId, String nickname) {
        this.postid = postId;
        this.nickname = nickname;
    }
}
