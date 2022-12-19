package com.example.haelogproject.post.dto;

import javax.persistence.Column;

public class PostRequestDto {

    @Column(nullable = false, unique = true)
    private Long memberId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String content;

    @Column(length = 150, nullable = true)
    private String contentSummary;


    public Long getMemberId() {
        return this.memberId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getContentSummary() {
        return this.contentSummary;
    }
}
