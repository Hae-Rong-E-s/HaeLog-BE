package com.example.haelogproject.post.exception;

import com.example.haelogproject.common.exception.ExceptionMessage;
import com.example.haelogproject.common.response.ResponseDto;
import com.example.haelogproject.post.exception.custom.PostNotFoundException;
import com.example.haelogproject.post.exception.custom.TagNotFoundException;
import com.example.haelogproject.post.exception.custom.UnauthorizedPostException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionHandler {
    //예외처리 응답용 ResponseEntity 만드는 메소드
    private ResponseEntity<ResponseDto> makeErrorResponseEntity(ExceptionMessage exceptionMessage){
        ResponseDto responseDto = new ResponseDto<>("fail", exceptionMessage.getMsg(), null);
        HttpStatus httpStatus = HttpStatus.valueOf(exceptionMessage.getStatusCode());

        return new ResponseEntity<>(responseDto, httpStatus);
    }
    //게시글 수정,삭제 시 게시글이 없을 경우
    @ExceptionHandler({PostNotFoundException.class})
    public ResponseEntity<ResponseDto> handlePostNotFoundException(PostNotFoundException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }

    //게시글 수정,삭제 시 본인이 아닐 경우
    @ExceptionHandler({UnauthorizedPostException.class})
    public ResponseEntity<ResponseDto> handleUnauthorizedPostException(UnauthorizedPostException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }

    //태그로 검색 시 태그가 없을 경우
    @ExceptionHandler({TagNotFoundException.class})
    public ResponseEntity<ResponseDto> handleTagNotFoundException(TagNotFoundException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }
}
