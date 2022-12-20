package com.example.haelogproject.comment.service;

import com.example.haelogproject.comment.dto.RequestCommentDto;
import com.example.haelogproject.comment.entity.Comment;
import com.example.haelogproject.comment.repository.CommentRepository;
import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.post.entity.Post;
import com.example.haelogproject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void writeComment(Long postId, RequestCommentDto requestCommentDto, Member member) {

        Post post = checkPost(postId);

        // Mapper 사용해서 dto -> entity 변경 리팩토링 필요
        Comment comment = new Comment(post, requestCommentDto, member);

        // post 연관관계 편의 메서드
        post.addComment(comment);

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long commentId, RequestCommentDto requestCommentDto, Member member) {

        String beforeComment = requestCommentDto.getContent();

        // 수정하려는 댓글 존재하는 지 확인 -> checkComment
        Comment comment = checkComment(commentId);
        comment.update(beforeComment);
    }

    @Transactional
    public void deleteComment(Long commentId, Member member) {

        // 삭제하려는 댓글 존재 확인 -> checkComment
        Comment comment = checkComment(commentId);

        commentRepository.delete(comment);
    }

    // 댓글 존재 여부 확인 메서드
    private Comment checkComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 댓글입니다."));
    }

    // 게시물 존재 여부 확인 메서드
    private Post checkPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 게시물입니다."));
    }
}
