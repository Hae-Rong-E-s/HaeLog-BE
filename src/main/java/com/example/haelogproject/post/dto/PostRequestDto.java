package com.example.haelogproject.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    private String title;

    private List<String> tags = new ArrayList<>();

    private String content;

    public PostRequestDto(String title, List<String> tags, String content) {
        this.title = title;
        this.tags = tags;
        this.content = content;
    }
}
