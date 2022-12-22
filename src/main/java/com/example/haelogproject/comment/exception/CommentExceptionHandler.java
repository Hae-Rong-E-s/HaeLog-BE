package com.example.haelogproject.comment.exception;

import com.example.haelogproject.comment.exception.custom.CommentNotFoundException;
import com.example.haelogproject.comment.exception.custom.UnauthorizedCommentException;
import com.example.haelogproject.common.exception.ExceptionMessage;
import com.example.haelogproject.common.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentExceptionHandler {

    //예외처리 응답용 ResponseEntity 만드는 메소드
    private ResponseEntity<ResponseDto> makeErrorResponseEntity(ExceptionMessage exceptionMessage){
        ResponseDto responseDto = new ResponseDto<>("fail", exceptionMessage.getMsg(), null);
        HttpStatus httpStatus = HttpStatus.valueOf(exceptionMessage.getStatusCode());

        return new ResponseEntity<>(responseDto, httpStatus);
    }
    //댓글 수정,삭제 시 댓글이 없을 경우
    @ExceptionHandler({CommentNotFoundException.class})
    public ResponseEntity<ResponseDto> handleCommentNotFoundException(CommentNotFoundException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }

    //댓글 수정,삭제 시 본인이 아닐 경우
    @ExceptionHandler({UnauthorizedCommentException.class})
    public ResponseEntity<ResponseDto> handleUnauthorizedCommentException(UnauthorizedCommentException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }
}
