package com.example.haelogproject.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// https 로드 밸런싱 대상 그룹의 healthy 체크로 해당 uri 요청에 대해 200 상태 코드로 응답
@RestController
public class HealthyCheckController {

    @GetMapping("/")
    public ResponseEntity healthyCheck() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
