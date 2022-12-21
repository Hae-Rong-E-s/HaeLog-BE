package com.example.haelogproject.post.dto;

import com.example.haelogproject.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {

    private Long commentId;
    private String commentContent;
    private String commentMemberNickname;
    private boolean myComment = true;
    private LocalDateTime createAt;

    public CommentResponseDto(Comment comment, boolean isMyService) {
        this.commentId = comment.getCommentId();
        this.commentContent = comment.getContent();
        this.commentMemberNickname = comment.getMember().getNickname();
        this.myComment = true;
        this.createAt = comment.getCreatedAt();
    }
}
