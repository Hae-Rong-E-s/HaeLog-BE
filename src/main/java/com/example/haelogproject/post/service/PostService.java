package com.example.haelogproject.post.service;

import com.example.haelogproject.common.response.ResponseDto;
import com.example.haelogproject.post.dto.PostRequestDto;
import com.example.haelogproject.post.entity.Post;
import com.example.haelogproject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    @Transactional
    public ResponseDto updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        post.update(postRequestDto.getTitle(), postRequestDto.getContent());
        return new ResponseDto("success", "게시물 수정 완료.");
    }
}
