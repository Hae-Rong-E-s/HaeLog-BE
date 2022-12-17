package com.example.haelogproject.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private final String result;
    private final String msg;
    private final Object data;

    @Builder
    public ResponseDto(String result, String msg, Object data){
        this.result = result;
        this.msg = msg;
        this.data = data;
    }
}
