package com.example.haelogproject.comment.controller;

import com.example.haelogproject.comment.dto.RequestCommentDto;
import com.example.haelogproject.comment.service.CommentService;
import com.example.haelogproject.common.response.ResponseDto;
import com.example.haelogproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<ResponseDto> writeComment(
            @PathVariable Long postId,
            @RequestBody RequestCommentDto requestCommentDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        commentService.writeComment(postId, requestCommentDto, userDetails.getMember());
        return new ResponseEntity<>(new ResponseDto<>("success", "댓글 작성에 성공하였습니다.", null), HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody RequestCommentDto requestCommentDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        commentService.updateComment(commentId, requestCommentDto, userDetails.getMember());
        return new ResponseEntity<>(new ResponseDto<>("success", "댓글 수정 완료.", null), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(commentId, userDetails.getMember());
        return new ResponseEntity<>(new ResponseDto<>("success", "댓글 삭제 완료.", null), HttpStatus.OK);
    }
}
