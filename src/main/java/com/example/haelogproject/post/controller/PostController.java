package com.example.haelogproject.post.controller;

import com.example.haelogproject.common.response.ResponseDto;
import com.example.haelogproject.post.dto.PostRequestDto;
import com.example.haelogproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto){
        ResponseDto response = postService.updatePost(postId, postRequestDto);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
