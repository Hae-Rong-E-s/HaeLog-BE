package com.example.haelogproject.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    // User
    USER_NOT_FOUND_ERROR_MSG(400,"회원을 찾을 수 없습니다!"),

    // JWT, OAuth
    TOKEN_NOT_FOUND_MSG(401,"토큰이 존재하지 않습니다."),
    INVALID_TOKEN_MSG(401,"토큰이 유효하지 않습니다."),

    REFRESH_TOKEN_NOT_FOUND_MSG(401, "로그아웃된 사용자입니다."),
    UNAUTHORIZED_USER(403, "인가되지 않은 사용자입니다"),

    // 403 토큰 만료
    EXPIRATION_TOKEN(403, "Access Token이 만료되었습니다"),

    // 403 권한 없음
    INVALID_AUTH_TOKEN(403,"권한이 없는 사용자 입니다");

    private final int statusCode;
    private final String msg;

    ExceptionMessage(final int statusCode, final String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
