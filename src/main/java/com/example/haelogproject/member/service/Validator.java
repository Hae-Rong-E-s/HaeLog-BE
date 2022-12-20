package com.example.haelogproject.member.service;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validator {
    public boolean validPassword(String password) {
        // "비밀번호는 영문, 숫자, 특수문자가 모두 포함된 8~16자리로 작성해주세요."
        final String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[a-zA-Z\\d~!@#$%^&*()+|=]{8,16}$";

        return Pattern.matches(regex, password);
    }

    public boolean validLoginId(String loginId) {
        // "아이디는 영문 소문자, 숫자가 모두 포함된 4~12자리로 작성해주세요."
        final String regex = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,12}$";

        return Pattern.matches(regex, loginId);
    }

    public boolean validNickname(String nickname) {
        // "닉네임은 영문 소문자, 한글, 숫자로 구성된 2~8자리로 작성해주세요."
        final String regex = "^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,8}$";

        return Pattern.matches(regex, nickname);
    }
}
