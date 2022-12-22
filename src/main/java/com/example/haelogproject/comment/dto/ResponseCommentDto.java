package com.example.haelogproject.comment.dto;

import com.example.haelogproject.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseCommentDto {

    private Long commentId;
    private String commentContent;
    private String commentMemberNickname;
    private boolean myComment = true;
    private LocalDateTime createAt;

    public ResponseCommentDto(Comment comment, boolean isMyComment) {
        this.commentId = comment.getCommentId();
        this.commentContent = comment.getContent();
        this.commentMemberNickname = comment.getMember().getNickname();
        this.myComment = isMyComment;
        this.createAt = comment.getCreatedAt();
    }
}
