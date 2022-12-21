package com.example.haelogproject.member.exception;

import com.example.haelogproject.common.exception.ExceptionMessage;
import com.example.haelogproject.member.dto.ResponseDto;
import com.example.haelogproject.member.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    //예외처리 응답용 ResponseEntity 만드는 메소드
    private ResponseEntity<ResponseDto> makeErrorResponseEntity(ExceptionMessage exceptionMessage){
        ResponseDto responseDto = new ResponseDto<>("fail", exceptionMessage.getMsg(), null);
        HttpStatus httpStatus = HttpStatus.valueOf(exceptionMessage.getStatusCode());

        return new ResponseEntity<>(responseDto, httpStatus);
    }
    //회원가입 시 Id 중복돨 경우
    @ExceptionHandler({DuplicateLoginIdException.class})
    public ResponseEntity<ResponseDto> handleDuplicateLoginIdException(DuplicateLoginIdException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }

    //회원가입 시 닉네임 중복될 경우
    @ExceptionHandler({DuplicateNicknameException.class})
    public ResponseEntity<ResponseDto> handleDuplicateNickNameException(DuplicateNicknameException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }

    //회원가입 시 로그인 양식이 일치하지 않을 경우
    @ExceptionHandler({InvalidLoginIdException.class})
    public ResponseEntity<ResponseDto> handleInvalidLoginIdException(InvalidLoginIdException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }

    //회원가입 시 닉네임 양식이 일치하지 않을 경우
    @ExceptionHandler({InvalidNicknameException.class})
    public ResponseEntity<ResponseDto> handleInvalidNicknameException(InvalidNicknameException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }

    //회원가입 시 비밀번호 양식이 일치하지 않을 경우
    @ExceptionHandler({InvalidPasswordException.class})
    public ResponseEntity<ResponseDto> handleInvalidPasswordException(InvalidPasswordException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }

    //로그인 시 일치하는 loginId가 없을 경우
    @ExceptionHandler({LoginIdNotFoundException.class})
    public ResponseEntity<ResponseDto> handleLoginIdNotFoundException(LoginIdNotFoundException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }

    //로그인 시 비밀번호가 일치하지 않을 경우
    @ExceptionHandler({WrongPasswordException.class})
    public ResponseEntity<ResponseDto> handleWrongPasswordException(WrongPasswordException e){
        return makeErrorResponseEntity(e.getExceptionMessage());
    }
}
