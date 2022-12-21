package com.example.haelogproject.member.exception.custom;

import com.example.haelogproject.common.exception.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class InvalidLoginIdException extends RuntimeException {
    private final ExceptionMessage exceptionMessage = ExceptionMessage.INVALID_LOGIN_ID;
}
