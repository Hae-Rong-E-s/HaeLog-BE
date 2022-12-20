package com.example.haelogproject.member.entity;

import com.example.haelogproject.common.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    // 닉네임을 통한 조회도 할 것이기 때문에 유니크 제약조건이 필요하다고 생각함.
    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = true)
    private String description;

    @Builder
    public Member(String loginId, String password, String nickname, String description) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.description = description;
    }
}
