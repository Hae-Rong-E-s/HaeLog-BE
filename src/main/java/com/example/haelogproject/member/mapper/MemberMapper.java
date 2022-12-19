package com.example.haelogproject.member.mapper;

import com.example.haelogproject.member.dto.RequestUserSignup;
import com.example.haelogproject.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toMember(RequestUserSignup requestUserSignup) {
        return new Member(requestUserSignup.getLoginId(),
                requestUserSignup.getPassword(),
                requestUserSignup.getNickname(),
                requestUserSignup.getDescription());
    }
}
