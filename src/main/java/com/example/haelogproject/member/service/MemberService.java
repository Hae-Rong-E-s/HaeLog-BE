package com.example.haelogproject.member.service;

import com.example.haelogproject.common.jwt.JwtUtil;
import com.example.haelogproject.member.dto.RequestUserLogin;
import com.example.haelogproject.member.dto.RequestUserSignup;
import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.member.mapper.MemberMapper;
import com.example.haelogproject.member.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRespository memberRespository;
    private final MemberMapper memberMapper;
    private final Validator validator;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(RequestUserSignup requestUserSignup) {
        // 1-1. loginId 체크
        checkLoginId(requestUserSignup);
        // 1-2. nickname 체크
        checkNickname(requestUserSignup);

        // 2. password 값 validation
        boolean checkPassword = validator.validPassword(requestUserSignup.getPassword());
        if(!checkPassword) {
            throw new IllegalArgumentException("비밀번호는 알파벳 대/소문자와 숫자, 특수문자로 구성된 10~15자리로 작성해주세요.");
        }
        // 3. Entity 생성 및 DB 저장
        Member member = memberMapper.toMember(requestUserSignup);
        memberRespository.save(member);
    }

    public void checkLoginId(RequestUserSignup requestUserSignup) {
        // 1. loginId 체크
            // 1-1. loginId 값 validation
        boolean checkLoginId = validator.validLoginId(requestUserSignup.getLoginId());
        if(!checkLoginId) {
            throw new IllegalArgumentException("아이디는 알파벳 소문자와 숫자로 구성된 4~10자리로 작성해주세요.");
        }
            // 1-2. 중복된 loginId 일 경우
        memberRespository.findByLoginId(requestUserSignup.getLoginId()).ifPresent(
                m -> {
                    throw new IllegalArgumentException("중복된 아이디 입니다.");
                }
        );
    }

    public void checkNickname(RequestUserSignup requestUserSignup) {
        // 1. nickname 체크
            // 1-1. nickname 값 validation
        boolean checkNickname = validator.validNickname(requestUserSignup.getNickname());
        if(!checkNickname) {
            throw new IllegalArgumentException("닉네임은 알파벳 대소문자와 숫자로 구성된 4~10자리로 작성해주세요.");
        }

            // 1-2. 중복된 nickname 일 경우
        memberRespository.findByNickname(requestUserSignup.getNickname()).ifPresent(
                m -> {
                    throw new IllegalArgumentException("중복된 닉네임 입니다.");
                }
        );
            // TODO
            //  1-3. loginId 와 중복될 경우(?)

    }

    public void login(RequestUserLogin requestUserLogin, HttpServletResponse response) {
        String loginId = requestUserLogin.getLoginId();
        String password = requestUserLogin.getPassword();

        // loginId 와 일치하는 회원정보가 있는지 확인.
        Member member = memberRespository.findByLoginId(loginId).orElseThrow(
                () -> new IllegalArgumentException("일치하는 회원정보가 없습니다.")
        );

        // 요청받은 password와 DB에 저장된 패스워드 비교.
        if(!password.equals(member.getPassword())) {
            throw new IllegalArgumentException("일치하는 회원정보가 없습니다.");
        }

        // 토큰 발급
        String accessToken = jwtUtil.createAccessToken(loginId);
        String refreshToken = jwtUtil.createRefreshToken();

        response.addHeader(JwtUtil.AUTHORIZATION_ACCESS, accessToken);
        response.addHeader(JwtUtil.AUTHORIZATION_REFRESH, refreshToken);
    }
}
