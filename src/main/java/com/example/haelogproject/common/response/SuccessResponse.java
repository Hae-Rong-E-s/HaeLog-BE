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

    //header 상태코드 : httpStatus / body "httpStatus": httpStatus, "msg": message, "data": data
    public ResponseEntity<ResponseDto> respond(HttpStatus httpStatus, String message, Object data){
        ResponseDto responseBody = buildResponseDto("success", message, data);
        return new ResponseEntity<>(responseBody, httpStatus);
    }


    //header 상태코드 : httpStatus / body "data": data
    public ResponseEntity<Object> respondDataOnly(HttpStatus httpStatus, Object data){
        return new ResponseEntity<>(data, httpStatus);
    }
}
