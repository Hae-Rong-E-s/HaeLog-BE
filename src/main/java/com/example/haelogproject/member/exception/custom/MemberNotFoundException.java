package com.example.haelogproject.member.exception.custom;

import com.example.haelogproject.common.exception.ExceptionMessage;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends RuntimeException{
    private final ExceptionMessage exceptionMessage = ExceptionMessage.MEMBER_NOT_FOUND;
}
