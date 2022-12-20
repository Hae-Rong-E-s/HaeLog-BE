package com.example.haelogproject.post.service;

import com.example.haelogproject.common.response.ResponseDto;
import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.post.dto.PostRequestDto;
import com.example.haelogproject.post.entity.Post;
import com.example.haelogproject.post.entity.PostTag;
import com.example.haelogproject.post.entity.Tag;
import com.example.haelogproject.post.mapper.PostMapper;
import com.example.haelogproject.post.repository.PostRepository;
import com.example.haelogproject.post.repository.PostTagRepository;
import com.example.haelogproject.post.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final PostMapper mapper;

    @Transactional
    public void writePost(PostRequestDto request, Member member) {
        // 본문 내용 미리보기를 저장 할 필드
        String summary;

        // 내용 미리보기 저장을 위해 150자 이상일 경우 subString으로 내용을 잘라준다.
        if (request.getContent().length() < 150) {
            summary = request.getContent();
        } else {
            summary = request.getContent().substring(0, 150);
        }

        // 게시물 저장
        Post post = mapper.toEntity(request, member, summary);
        postRepository.save(post);

        for (String tag : request.getTags()) {
            // 요청으로 들어온 tag리스트를 전부 확인하면서 이미 만들어진 태그 이름이 있는 지 확인
            Optional<Tag> savedTag = tagRepository.findByTagNameAndMember_Id(tag, member.getMemberId());

            // 중복이 있다면 해당 아이디로 태그 저장, 중복이 없다면 새로운 태그 생성
            if (savedTag.isPresent()) {
                PostTag newPostTag = new PostTag(post, savedTag.get());
                postTagRepository.save(newPostTag);
            } else {
                Tag newTag = new Tag(tag, member);
                tagRepository.save(newTag);

                PostTag newPostTag = new PostTag(post, newTag);
                postTagRepository.save(newPostTag);
            }
        }
    }

    @Transactional
    public ResponseDto updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        post.update(postRequestDto.getTitle(), postRequestDto.getContent());
        return new ResponseDto("success", "게시물 수정 완료.", null);
    }
}
