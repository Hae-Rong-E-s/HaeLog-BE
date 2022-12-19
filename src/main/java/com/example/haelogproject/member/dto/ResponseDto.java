package com.example.haelogproject.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private final String result;
    private final String msg;
    private final Object data;

    public ResponseDto(String result, String msg, Object data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }
}
