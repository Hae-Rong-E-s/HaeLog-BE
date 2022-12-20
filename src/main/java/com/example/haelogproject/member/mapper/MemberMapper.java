package com.example.haelogproject.member.mapper;

import com.example.haelogproject.member.dto.RequestUserSignup;
import com.example.haelogproject.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toMember(RequestUserSignup requestUserSignup) {
        //  엔티티의 @Builder 를 통해 사용되어짐.
        return Member.builder()
                .loginId(requestUserSignup.getLoginId())
                .nickname(requestUserSignup.getNickname())
                .description(requestUserSignup.getDescription())
                .password(requestUserSignup.getPassword())
                .build();
    }
}
