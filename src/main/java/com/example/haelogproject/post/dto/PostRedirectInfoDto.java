package com.example.haelogproject.post.dto;

import lombok.Getter;

@Getter
public class PostRedirectInfoDto {
    private Long postid;
    private String nickname;

    public PostRedirectInfoDto(Long postId, String nickname) {
        this.postid = postId;
        this.nickname = nickname;
    }
}
