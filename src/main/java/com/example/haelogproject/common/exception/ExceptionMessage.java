package com.example.haelogproject.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    //Member 도메인
    DUPLICATE_LOGIN_ID (400, "중복된 아이디가 존재합니다."),
    DUPLICATE_NICKNAME (400, "중복된 닉네임이 존재합니다."),
    INVALID_LOGIN_ID (400, "아이디는 영문 소문자, 숫자가 모두 포함된 4~12 자리로 작성해주세요."),
    INVALID_PASSWORD (400, "비밀번호는 영문, 숫자, 특수문자가 모두 포함된 8~16 자리로 작성해주세요."),
    INVALID_NICKNAME (400, "닉네임은 영문 소문자, 한글, 숫자로 구성된 2~8 자리로 작성해주세요."),
    MEMBER_NOT_FOUND (404, "해당 유저가 존재하지 않습니다."),
    LOGIN_ID_NOT_FOUND (404, "일치하는 회원 정보가 없습니다."), //로그인 하려는 loginId가 없을 경우
    WRONG_PASSWORD (404, "일치하는 회원 정보가 없습니다."), //로그인 하려는 비밀번호가 틀렸을 경우

    //Post 도메인
    POST_NOT_FOUND (404, "게시글이 존재하지 않습니다."),
    UNAUTHORIZED_POST (401, "게시글 수정 권한이 없습니다."),
    TAG_NOT_FOUND (404, "해당 태그가 존재하지 않습니다."),

    //Comment 도메인
    COMMENT_NOT_FOUND (404, "댓글이 존재하지 않습니다."),
    UNAUTHORIZED_COMMENT (401, "댓글 수정 권한이 없습니다."),

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
