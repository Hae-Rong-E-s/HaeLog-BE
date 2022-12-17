package com.example.haelogproject.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SuccessResponse {

    private ResponseDto buildResponseDto(String result, String msg, Object data){
        return ResponseDto.builder()
                .result(result)
                .msg(msg)
                .data(data)
                .build();
    }

    //header 상태코드 : httpStatus / body {result: success , msg: msg, data: data}
    public ResponseEntity<ResponseDto> respond(HttpStatus httpStatus, String msg, Object data){
        ResponseDto responseBody = buildResponseDto("success", msg, data);
        return new ResponseEntity<>(responseBody, httpStatus);
    }
}
