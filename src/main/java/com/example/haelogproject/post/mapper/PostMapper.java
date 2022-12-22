package com.example.haelogproject.post.mapper;

import com.example.haelogproject.comment.dto.ResponseCommentDto;
import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.post.dto.*;
import com.example.haelogproject.post.entity.Post;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public PostDetailResponseDto toDetailDto(Post post, Member member, boolean isAuthor, List<String> tagList, List<ResponseCommentDto> responseCommentDtoList) {
        return PostDetailResponseDto.builder()
                .postid(post.getPostId())
                .createdAt(post.getCreatedAt())
                .title(post.getTitle())
                .tags(tagList)
                .postMemberNikname(member.getNickname())
                .isMyPost(isAuthor)
                .description(member.getDescription())
                .postContent(post.getContent())
                .contentSummary(post.getContentSummary())
                .responseCommentDtoList(responseCommentDtoList)
                .build();
    }

    public PostSimpleResponseDto toSimpleDto(Post post, Member author, List<String> tags, Long commentCount) {
        return PostSimpleResponseDto.builder()
                .postid(post.getPostId())
                .createdAt(post.getCreatedAt())
                .title(post.getTitle())
                .tags(tags)
                .contentSummary(post.getContentSummary())
                .nickname(author.getNickname())
                .commentCount(commentCount)
                .build();
    }

    public PostInfoForUpdateDto toInfoDto(Post post, Member author, List<String> tags) {
        return PostInfoForUpdateDto.builder()
                .postid(post.getPostId())
                .createdAt(post.getCreatedAt())
                .title(post.getTitle())
                .tags(tags)
                .nickname(author.getNickname())
                .content(post.getContent())
                .build();
    }
}
