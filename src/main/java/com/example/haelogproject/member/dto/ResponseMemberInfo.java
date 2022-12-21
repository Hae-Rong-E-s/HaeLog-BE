package com.example.haelogproject.member.dto;

import lombok.Getter;

@Getter
public class ResponseMemberInfo {
    private String description;
    private boolean myInfo;

    public ResponseMemberInfo(String description, boolean myInfo) {
        this.description = description;
        this.myInfo = myInfo;
    }
}
