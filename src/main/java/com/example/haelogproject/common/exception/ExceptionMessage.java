package com.example.haelogproject.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {





    // JWT, OAuth
    TOKEN_NOT_FOUND_MSG(401,"토큰이 존재하지 않습니다."),
    INVALID_TOKEN_MSG(401,"토큰이 유효하지 않습니다.");

    private final int statusCode;
    private final String msg;

    ExceptionMessage(final int statusCode, final String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
