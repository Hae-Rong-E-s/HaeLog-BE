package com.example.haelogproject.member.exception.custom;

import com.example.haelogproject.common.exception.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class InvalidPasswordException extends RuntimeException {
    private final ExceptionMessage exceptionMessage = ExceptionMessage.INVALID_PASSWORD;
}
