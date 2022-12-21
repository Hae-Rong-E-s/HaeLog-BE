package com.example.haelogproject.member.exception.custom;

import com.example.haelogproject.common.exception.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class LoginIdNotFoundException extends RuntimeException{
    private final ExceptionMessage exceptionMessage = ExceptionMessage.LOGIN_ID_NOT_FOUND;
}
