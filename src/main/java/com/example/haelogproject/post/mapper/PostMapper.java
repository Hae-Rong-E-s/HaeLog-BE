package com.example.haelogproject.post.mapper;

import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.post.dto.PostRequestDto;
import com.example.haelogproject.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toEntity(PostRequestDto request, Member member, String summary) {
        return Post.builder()
                .memberId(member.getMemberId())
                .title(request.getTitle())
                .content(request.getContent())
                .contentSummary(summary)
                .build();
    }
}
