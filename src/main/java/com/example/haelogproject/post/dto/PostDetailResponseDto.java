package com.example.haelogproject.post.dto;

import com.example.haelogproject.comment.dto.ResponseCommentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDetailResponseDto {

    private Long postid;
    private LocalDateTime createdAt;
    private String title;
    private List<String> tags;
    private String postMemberNickname;
    private String description;
    private String postContent;
    private boolean isMyPost;
    private String contentSummary;
    private List<ResponseCommentDto> commentList = new ArrayList<>();

    @Builder
    public PostDetailResponseDto(Long postid, LocalDateTime createdAt, String title, List<String> tags, String postMemberNikname, String description, String postContent, boolean isMyPost, String contentSummary, List<ResponseCommentDto> responseCommentDtoList) {
        this.postid = postid;
        this.createdAt = createdAt;
        this.title = title;
        this.tags = tags;
        this.postMemberNickname = postMemberNikname;
        this.description = description;
        this.postContent = postContent;
        this.isMyPost = isMyPost;
        this.contentSummary = contentSummary;
        this.commentList = responseCommentDtoList;
    }
}
