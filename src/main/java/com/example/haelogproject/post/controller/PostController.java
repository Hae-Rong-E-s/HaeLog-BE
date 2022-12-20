package com.example.haelogproject.post.controller;

import com.example.haelogproject.common.response.ResponseDto;
import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.post.dto.PostRequestDto;
import com.example.haelogproject.post.dto.PostDetailResponseDto;
import com.example.haelogproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    // 게시물 생성
    @PostMapping("")
    public ResponseEntity<ResponseDto> writePost(@RequestBody PostRequestDto request, Member member) {
        postService.writePost(request, member);
        return new ResponseEntity(new ResponseDto("success", "게시물 등록이 완료되었습니다.", null), HttpStatus.OK);
    }


    // 게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto response, Member member){
        postService.updatePost(postId, response, member);
        return new ResponseEntity(new ResponseDto("success", "게시물 등록이 완료되었습니다.", null), HttpStatus.OK);
    }

    // 게시물 석제
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long postId, Member member) {
        postService.deletePost(postId, member);
        return new ResponseEntity(new ResponseDto("success", "게시물 삭제 완료.", null), HttpStatus.OK);
    }

    // 게시줄 조회 (게시물 상세 조회)
    @GetMapping("")
    public ResponseEntity<ResponseDto> getDetailPost(@RequestParam String nickname, @RequestParam Long postid, HttpServletRequest request) {
        PostDetailResponseDto response = postService.getDetailPost(nickname, postid, request);
        return new ResponseEntity(new ResponseDto<PostDetailResponseDto>("success", "게시물 조회 성공", response), HttpStatus.OK);
    }
}