package com.example.haelogproject.comment.mapper;

import com.example.haelogproject.comment.dto.RequestCommentDto;
import com.example.haelogproject.comment.entity.Comment;
import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toComment(Post post, Member member, RequestCommentDto requestCommentDto) {
        return Comment.builder()
                .content(requestCommentDto.getContent())
                .member(member)
                .post(post)
                .build();
    }
}
