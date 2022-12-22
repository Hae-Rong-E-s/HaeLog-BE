package com.example.haelogproject.member.controller;

import com.example.haelogproject.common.response.ResponseDto;
import com.example.haelogproject.member.dto.RequestUserLogin;
import com.example.haelogproject.member.dto.RequestUserSignup;
import com.example.haelogproject.member.dto.ResponseMemberInfo;
import com.example.haelogproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody RequestUserSignup requestUserSignup) {
        memberService.signup(requestUserSignup);

        return new ResponseEntity<>(new ResponseDto("success", "회원가입이 완료되었습니다.", null), HttpStatus.OK);
    }


    // loginId validation 및 중복확인
    @PostMapping("/signup/loginid")
    public ResponseEntity<ResponseDto> checkLoginId(@RequestBody RequestUserSignup requestUserSignup) {
        memberService.checkLoginId(requestUserSignup);

        return new ResponseEntity<>(new ResponseDto("success", "사용 가능한 아이디입니다.", null), HttpStatus.OK);
    }


    // nickname validation 및 중복확인
    @PostMapping("/signup/nickname")
    public ResponseEntity<ResponseDto> checkNickname(@RequestBody RequestUserSignup requestUserSignup) {
        memberService.checkNickname(requestUserSignup);

        return new ResponseEntity<>(new ResponseDto("success", "사용 가능한 닉네임입니다.", null), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody RequestUserLogin requestUserLogin, HttpServletResponse response) {
        ResponseDto responseDto = memberService.login(requestUserLogin, response);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 회원 정보 조회
    @GetMapping("/info")
    public ResponseEntity<ResponseDto> getMemberInfo(@RequestParam String nickname, HttpServletRequest request) {
        ResponseMemberInfo response = memberService.getUserInfo(nickname, request);
        return new ResponseEntity<>(new ResponseDto("success", "회원 정보 조회 성공", response), HttpStatus.OK);
    }
}
