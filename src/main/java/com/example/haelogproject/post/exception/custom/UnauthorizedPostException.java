package com.example.haelogproject.post.exception.custom;

import com.example.haelogproject.common.exception.ExceptionMessage;
import lombok.Getter;

@Getter
public class UnauthorizedPostException extends RuntimeException{
    private final ExceptionMessage exceptionMessage = ExceptionMessage.UNAUTHORIZED_POST;
}
