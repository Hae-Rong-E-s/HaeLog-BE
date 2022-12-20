package com.example.haelogproject.member.dto;

import lombok.Getter;

@Getter
public class RequestUserSignup {
    private String loginId;

    private String password;

    private String nickname;

    private String description;
}
