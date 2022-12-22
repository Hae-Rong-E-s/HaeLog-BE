package com.example.haelogproject.comment.exception.custom;

import com.example.haelogproject.common.exception.ExceptionMessage;
import lombok.Getter;

@Getter
public class CommentNotFoundException extends RuntimeException{
    private final ExceptionMessage exceptionMessage = ExceptionMessage.COMMENT_NOT_FOUND;
}
