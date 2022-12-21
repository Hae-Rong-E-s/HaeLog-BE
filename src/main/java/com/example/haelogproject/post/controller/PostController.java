package com.example.haelogproject.post.controller;

import com.example.haelogproject.common.response.ResponseDto;
import com.example.haelogproject.common.security.UserDetailsImpl;
import com.example.haelogproject.post.dto.PostDetailResponseDto;
import com.example.haelogproject.post.dto.PostInfoForUpdateDto;
import com.example.haelogproject.post.dto.PostRequestDto;
import com.example.haelogproject.post.dto.PostSimpleResponseDto;
import com.example.haelogproject.post.dto.PostRedirectInfoDto;
import com.example.haelogproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    // 게시물 생성
    @PostMapping("/post")
    public ResponseEntity<ResponseDto> writePost(@RequestBody PostRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostRedirectInfoDto response = postService.writePost(request, userDetails.getMember());
        return new ResponseEntity(new ResponseDto("success", "게시물 등록이 완료되었습니다.", response), HttpStatus.OK);
    }


    // 게시물 수정
    @PutMapping("/post/{postId}")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails){
        PostRedirectInfoDto response = postService.updatePost(postId, request, userDetails.getMember());
        return new ResponseEntity(new ResponseDto("success", "게시물 수정이 완료", response), HttpStatus.OK);
    }

    // 게시물 삭제
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getMember());
        return new ResponseEntity(new ResponseDto("success", "게시물 삭제 완료.", null), HttpStatus.OK);
    }

    // 게시줄 조회 (게시물 상세 조회)
    @GetMapping("")
    public ResponseEntity<ResponseDto> getDetailPost(@RequestParam String nickname, @RequestParam Long postid, HttpServletRequest request) {
        PostDetailResponseDto response = postService.getDetailPost(nickname, postid, request);
        return new ResponseEntity(new ResponseDto("success", "게시물 조회 성공", response), HttpStatus.OK);
    }

    // 멤버가 작성한 모든 게시물 조회
    @GetMapping("/{nickname}")
    public ResponseEntity<ResponseDto> getUserPostList(@PathVariable String nickname) {
        List<PostSimpleResponseDto> response = postService.getUserPostList(nickname);
        return new ResponseEntity<>(new ResponseDto("success", "유저의 게시물 조회 성공", response), HttpStatus.OK);
    }

    // 맴버가 작성한 게시물을 태그 조회
    @GetMapping("/{nickname}/post")
    public ResponseEntity<ResponseDto> getUserPostListByTag(@PathVariable String nickname, @RequestParam String tag) {
        List<PostSimpleResponseDto> response = postService.getUserPostListByTag(nickname, tag);
        return new ResponseEntity(new ResponseDto("success", "유저의 게시물 조회 성공", response), HttpStatus.OK);
    }

    // 게시물 수정 페이지에 필요한 정보 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<ResponseDto> getPostInfoForUpdate(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostInfoForUpdateDto response = postService.getPostInfoForUpdate(postId, userDetails.getMember());
        return new ResponseEntity(new ResponseDto("success", "게시물 수정에 필요한 정보를 반환했습니다.", response), HttpStatus.OK);
    }
}