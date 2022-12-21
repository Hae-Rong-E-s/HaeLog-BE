package com.example.haelogproject.member.mapper;

import com.example.haelogproject.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toMember(String loginId, String password, String nickname, String description) {
        //  엔티티의 @Builder 를 통해 사용되어짐.
        return Member.builder()
                .loginId(loginId)
                .nickname(nickname)
                .description(description)
                .password(password)
                .build();
    }
}
