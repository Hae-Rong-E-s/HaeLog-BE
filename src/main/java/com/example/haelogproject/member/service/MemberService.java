package com.example.haelogproject.member.service;

import com.example.haelogproject.common.jwt.JwtUtil;
import com.example.haelogproject.common.response.ResponseDto;
import com.example.haelogproject.member.dto.RequestUserLogin;
import com.example.haelogproject.member.dto.RequestUserSignup;
import com.example.haelogproject.member.dto.ResponseMemberInfo;
import com.example.haelogproject.member.dto.ResponseUserLogin;
import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.member.mapper.MemberMapper;
import com.example.haelogproject.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

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
            throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자가 모두 포함된 8~16자리로 작성해주세요.");
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
            throw new IllegalArgumentException("아이디는 영문 소문자, 숫자가 모두 포함된 4~12자리로 작성해주세요.");
        }
            // 1-2. 중복된 loginId 일 경우
        memberRepository.findByLoginId(requestUserSignup.getLoginId()).ifPresent(
                m -> {
                    throw new IllegalArgumentException("중복된 아이디 입니다.");
                }
        );
    }

    @Transactional
    public void checkNickname(RequestUserSignup requestUserSignup) {
        // 1. nickname 체크
            // 1-1. nickname 값 validation
        boolean checkNickname = validator.validNickname(requestUserSignup.getNickname());
        if(!checkNickname) {
            throw new IllegalArgumentException("닉네임은 영문 소문자, 한글, 숫자로 구성된 2~8자리로 작성해주세요.");
        }
            // 1-2. 중복된 nickname 일 경우
        memberRepository.findByNickname(requestUserSignup.getNickname()).ifPresent(
                m -> {
                    throw new IllegalArgumentException("중복된 닉네임 입니다.");
                }
        );
    }

    @Transactional
    public ResponseDto<ResponseUserLogin> login(RequestUserLogin requestUserLogin, HttpServletResponse response) {
        String loginId = requestUserLogin.getLoginId();

        // loginId 와 일치하는 회원정보가 있는지 확인.
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new IllegalArgumentException("일치하는 회원정보가 없습니다.")
        );

        // 요청받은 password 와 DB에 저장된 패스워드 비교.
        if(!passwordEncoder.matches(requestUserLogin.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("일치하는 회원정보가 없습니다.");
        }

        // accessToken 토큰 발급
        String accessToken = jwtUtil.createAccessToken(loginId);

        response.addHeader(JwtUtil.AUTHORIZATION_ACCESS, accessToken);

        return new ResponseDto<>("success", "로그인이 완료되었습니다.", new ResponseUserLogin(member.getNickname()));
    }

    public ResponseMemberInfo getUserInfo(String nickname, HttpServletRequest request) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new IllegalArgumentException("일치하는 회원 정보가 없습니다.")
        );

        // 요청에 JWT 포함 여부 확인
        String token = jwtUtil.resolveToken(request, "Authorization");
        boolean isLogin = (token != null) ? true : false;

        // 요청한 회원과 요청한 정보의 회원 일치 여부를 저장하는 필드
        boolean myInfo = false;

        // 만약 JWT가 포함되어있다면 조회한 맴버외 비교
        if (isLogin) {
            // jwt 안의 정보를 이용하여 유저 조회
            Claims claims = jwtUtil.getUserInfoFromToken(token);
            String requestLoginId = claims.getSubject();
            if (member.getLoginId().equals(requestLoginId)) {
                myInfo = true;
            }
        }

        return new ResponseMemberInfo(member.getDescription(), myInfo);
    }
}
