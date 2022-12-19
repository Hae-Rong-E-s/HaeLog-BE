package com.example.haelogproject.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private String result;
    private String msg;
    private T data;

    public ResponseDto(String result, String msg, T data){
        this.result = result;
        this.msg = msg;
        this.data = data;
    }

    public ResponseDto(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }
}
