package com.example.haelogproject.comment.service;

import com.example.haelogproject.comment.dto.RequestCommentDto;
import com.example.haelogproject.comment.dto.ResponseCommentDto;
import com.example.haelogproject.comment.entity.Comment;
import com.example.haelogproject.comment.mapper.CommentMapper;
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
    private final CommentMapper commentMapper;

    @Transactional
    public ResponseCommentDto writeComment(Long postId, RequestCommentDto requestCommentDto, Member member) {
        // 게시물 존재 여부 확인
        Post post = checkPost(postId);
        Comment comment = commentMapper.toComment(post, member, requestCommentDto);

        // post 연관관계 편의 메서드
        post.addComment(comment);

        commentRepository.save(comment);


        // 댓글 작성자와 동일한지 상태 반환
        boolean isCommenter = false;

        if (member.getMemberId().equals(comment.getMember().getMemberId())) {
            isCommenter = true;
        }

        return new ResponseCommentDto(comment, isCommenter);
    }

    @Transactional
    public void updateComment(Long commentId, RequestCommentDto requestCommentDto, Member member) {

        String beforeComment = requestCommentDto.getContent();

        // 수정하려는 댓글 존재하는 지 확인 -> checkComment
        Comment comment = checkComment(commentId);

        // 댓글 권한 검증
        validateAuth(member, comment);

        comment.update(beforeComment);
    }

    @Transactional
    public void deleteComment(Long commentId, Member member) {
        // 삭제하려는 댓글 존재 확인 -> checkComment
        Comment comment = checkComment(commentId);

        // 댓글 권한 검증
        validateAuth(member, comment);

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

    // 댓글 권한 검증 메서드
    private void validateAuth(Member member, Comment comment) {
        if (!member.getMemberId().equals(comment.getMember().getMemberId())) {
            throw new IllegalArgumentException("댓글을 관리할 권한이 없습니다.");
        }
    }
}
