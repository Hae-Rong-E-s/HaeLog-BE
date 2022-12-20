package com.example.haelogproject.common.jwt.exception;

import com.example.haelogproject.common.exception.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomSecurityException extends RuntimeException{
    private final ExceptionMessage exceptionMessage;
}
