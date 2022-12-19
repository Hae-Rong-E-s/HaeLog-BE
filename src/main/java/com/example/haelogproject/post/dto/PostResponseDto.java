package com.example.haelogproject.post.dto;


import lombok.Getter;



@Getter
public class PostResponseDto {

    private String result;
    private String msg;

    public PostResponseDto(String result, String msg) {
        this.result = result;
        this.msg = msg;

    }

}
