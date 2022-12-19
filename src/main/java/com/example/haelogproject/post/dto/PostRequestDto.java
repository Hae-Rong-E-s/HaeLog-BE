package com.example.haelogproject.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    @Column(nullable = false, unique = true)
    private Long memberId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String content;

    @Column(length = 150, nullable = true)
    private String contentSummary;

    public PostRequestDto(Long memberId, String title, String content, String contentSummary) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.contentSummary = contentSummary;
    }
}
