package com.example.haelogproject.member.entity;

import javax.persistence.*;

import com.example.haelogproject.common.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String description;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    @Builder
    public Member(String loginId, String password, String nickname, String description){
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.description = description;
    }

    public void updateToken(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
