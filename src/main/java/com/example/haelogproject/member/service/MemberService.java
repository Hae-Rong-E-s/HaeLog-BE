package com.example.haelogproject.member.service;

import com.example.haelogproject.common.jwt.JwtUtil;
import com.example.haelogproject.common.response.ResponseDto;
import com.example.haelogproject.member.dto.RequestUserLogin;
import com.example.haelogproject.member.dto.RequestUserSignup;
import com.example.haelogproject.member.dto.ResponseUserLogin;
import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.member.exception.custom.*;
import com.example.haelogproject.member.mapper.MemberMapper;
import com.example.haelogproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final Validator validator;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(RequestUserSignup requestUserSignup) {
        // 1. loginId 체크
        checkLoginId(requestUserSignup);

        // 2. nickname 체크
        checkNickname(requestUserSignup);

        // 3. password 값 validation
        boolean checkPassword = validator.validPassword(requestUserSignup.getPassword());
        if(!checkPassword) {
            throw new InvalidPasswordException();
        }

        // 4. 비밀번호 암호화 및 모든 정보 꺼내오기
        String loginId = requestUserSignup.getLoginId();
        String password = passwordEncoder.encode(requestUserSignup.getPassword());
        String nickname = requestUserSignup.getNickname();
        String description = requestUserSignup.getDescription();


        // 5. Entity 생성 및 DB 저장
        Member member = memberMapper.toMember(loginId, password, nickname, description);
        memberRepository.save(member);
    }

    @Transactional
    public void checkLoginId(RequestUserSignup requestUserSignup) {
        // 1. loginId 체크
            // 1-1. loginId 값 validation
        boolean checkLoginId = validator.validLoginId(requestUserSignup.getLoginId());
        if(!checkLoginId) {
            throw new InvalidLoginIdException();
        }
            // 1-2. 중복된 loginId 일 경우
        memberRepository.findByLoginId(requestUserSignup.getLoginId()).ifPresent(
                m -> {
                    throw new DuplicateLoginIdException();
                }
        );
    }

    @Transactional
    public void checkNickname(RequestUserSignup requestUserSignup) {
        // 1. nickname 체크
            // 1-1. nickname 값 validation
        boolean checkNickname = validator.validNickname(requestUserSignup.getNickname());
        if(!checkNickname) {
            throw new InvalidNicknameException();
        }
            // 1-2. 중복된 nickname 일 경우
        memberRepository.findByNickname(requestUserSignup.getNickname()).ifPresent(
                m -> {
                    throw new DuplicateNicknameException();
                }
        );
    }

    @Transactional
    public ResponseDto<ResponseUserLogin> login(RequestUserLogin requestUserLogin, HttpServletResponse response) {
        String loginId = requestUserLogin.getLoginId();

        // loginId 와 일치하는 회원정보가 있는지 확인.
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                LoginIdNotFoundException::new
        );

        // 요청받은 password 와 DB에 저장된 패스워드 비교.
        if(!passwordEncoder.matches(requestUserLogin.getPassword(), member.getPassword())) {
            throw new WrongPasswordException();
        }

        // accessToken 토큰 발급
        String accessToken = jwtUtil.createAccessToken(loginId);

        response.addHeader(JwtUtil.AUTHORIZATION_ACCESS, accessToken);

        return new ResponseDto<>("success", "로그인이 완료되었습니다.", new ResponseUserLogin(member.getNickname()));
    }
}
