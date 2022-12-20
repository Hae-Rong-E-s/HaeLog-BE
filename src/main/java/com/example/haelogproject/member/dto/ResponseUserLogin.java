package com.example.haelogproject.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserLogin {
    private String nickname;

    public ResponseUserLogin(String nickname) {
        this.nickname = nickname;
    }
}
