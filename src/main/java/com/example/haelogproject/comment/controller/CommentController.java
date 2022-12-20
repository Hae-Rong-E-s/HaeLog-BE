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

        // 뭐가 더 좋을까요? 30 라인 코드와 41라인 코드 비교.
        commentService.writeComment(postId, requestCommentDto, userDetails.getMember());
        // 1번
        return new ResponseEntity<>(new ResponseDto<>("success", "댓글 작성에 성공하였습니다.", null), HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody RequestCommentDto requestCommentDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.updateComment(commentId, requestCommentDto, userDetails.getMember());
        // 2번
        ResponseDto responseDto = new ResponseDto<>("success", "댓글 수정 완료.", null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(commentId, userDetails.getMember());
        ResponseDto responseDto = new ResponseDto("success", "댓글 삭제 완료.", null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
