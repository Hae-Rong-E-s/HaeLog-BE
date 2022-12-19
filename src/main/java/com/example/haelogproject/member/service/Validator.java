package com.example.haelogproject.member.service;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validator {
    public boolean validPassword(String password) {
        // "비밀번호는 알파벳과 숫자, 특수문자로 구성된 10~15자리로 작성해주세요."
        final String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,15}$";

        return Pattern.matches(regex, password);
    }

    public boolean validLoginId(String loginId) {
        // "아이디는 알파벳 소문자와 숫자로 구성된 4~10자리로 작성해주세요."
        final String regex = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,10}$";

        return Pattern.matches(regex, loginId);
    }

    public boolean validNickname(String nickname) {
        // TODO
        //  닉네임 조건을 바꾸는게 좋지 않을까?

        // "닉네임은 알파벳 대소문자와 숫자로 구성된 2~8자리로 작성해주세요."
        final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{2,8}$";

        return Pattern.matches(regex, nickname);
    }
}
