package com.example.haelogproject.post.service;

import com.example.haelogproject.comment.dto.ResponseCommentDto;
import com.example.haelogproject.comment.entity.Comment;
import com.example.haelogproject.comment.repository.CommentRepository;
import com.example.haelogproject.common.jwt.JwtUtil;
import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.member.exception.custom.MemberNotFoundException;
import com.example.haelogproject.member.repository.MemberRepository;
import com.example.haelogproject.post.dto.*;
import com.example.haelogproject.post.entity.Post;
import com.example.haelogproject.post.entity.PostTag;
import com.example.haelogproject.post.entity.Tag;
import com.example.haelogproject.post.exception.custom.PostNotFoundException;
import com.example.haelogproject.post.exception.custom.TagNotFoundException;
import com.example.haelogproject.post.exception.custom.UnauthorizedPostException;
import com.example.haelogproject.post.mapper.PostMapper;
import com.example.haelogproject.post.repository.PostRepository;
import com.example.haelogproject.post.repository.PostTagRepository;
import com.example.haelogproject.post.repository.TagRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostMapper mapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public PostRedirectInfoDto writePost(PostRequestDto request, Member member) {
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

        // 태그 저장
        for (String tag : request.getTags()) {
            // 요청으로 들어온 tag리스트를 전부 확인하면서 이미 만들어진 태그 이름이 있는 지 확인
            Optional<Tag> savedTag = tagRepository.findByMemberAndTagName(member, tag);

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

        return new PostRedirectInfoDto(post.getPostId(), member.getNickname());
    }

    @Transactional
    public PostRedirectInfoDto updatePost(Long postId, PostRequestDto request, Member member) {

        // 기존에 저장된 게시물 불러오기
        Post post = postRepository.findById(postId).orElseThrow(
                PostNotFoundException::new
        );

        // 게시물의 작성자가 요청한 사용자와 일치하는지 확인
        if (!post.getMemberId().equals(member.getMemberId())) {
            throw new UnauthorizedPostException();
        }

        // 본문 내용 미리보기를 저장 할 필드
        String summary;
        // 내용 미리보기 저장을 위해 150자 이상일 경우 subString으로 내용을 잘라준다.
        if (request.getContent().length() < 150) {
            summary = request.getContent();
        } else {
            summary = request.getContent().substring(0, 150);
        }

        // 기존에 해당 포스트에 저장된 태그를 전부 삭제하고 새로 생성
        postTagRepository.deleteAllByPost(post);

        for (String tag : request.getTags()) { // 태그가 무조건 하나가 있어야되나?
            // 요청으로 들어온 tag리스트를 전부 확인하면서 이미 만들어진 태그 이름이 있는 지 확인
            Optional<Tag> savedTag = tagRepository.findByMemberAndTagName(member, tag);

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

        // 게시물 내용 변경
        post.update(request.getTitle(), request.getContent(), summary);

        // 수정 정보 반환
        return new PostRedirectInfoDto(post.getPostId(), member.getNickname());
    }

    @Transactional
    public void deletePost(Long postId, Member member) {
        // 기존에 저장된 게시물 불러오기
        Post post = postRepository.findById(postId).orElseThrow(
                PostNotFoundException::new
        );

        // 게시물의 작성자가 요청한 사용자와 일치하는지 확인 일치하지 않을 경우 예외 발생
        if (!post.getMemberId().equals(member.getMemberId())) {
            throw new UnauthorizedPostException();
        }

        // 태그(PostTag) 삭제
        postTagRepository.deleteAllByPost(post);

        // 태그 이름(Tag)을 사용하는 게시물이 없을 경우 태그도 삭제
        List<Tag> tags = tagRepository.findAllByMember(member);
        for (Tag tag : tags) {
            if (!postTagRepository.existsByTag(tag)) {
                tagRepository.delete(tag);
            }
        }

        // 댓글 삭제
        commentRepository.deleteAllByPost(post);

        // 게시물 삭제
        postRepository.deleteById(postId);
    }

    // 게시물 상세 조회
    @Transactional(readOnly = true)
    public PostDetailResponseDto getDetailPost(String nickname, Long postid, HttpServletRequest request) {

        Post post = postRepository.findById(postid).orElseThrow(
                PostNotFoundException::new
        );
        // 작성자 정보 가져오기
        Member member = memberRepository.findByMemberId(post.getMemberId());

        // 로그인 상태 확인
        String token = jwtUtil.resolveToken(request, "authorization");
        boolean isLogin = (token != null) ? true : false;

        Member requestMember = new Member();
        Claims claims = null;

        // jwt 내부 값 확인
        if (isLogin) {
            claims = jwtUtil.getUserInfoFromToken(token);

            // jwt 안의 정보를 이용하여 유저 조회
            Optional<Member> memberInJwt = memberRepository.findByLoginId(claims.getSubject());
            if (memberInJwt.isPresent()) {
                requestMember = memberInJwt.get();
            }
        }

        // 요청한 맴버와 게시글의 작성자 일치 여부
        boolean isAuthor = false;

        // 로그인 상태라면 조회 요청한 유저와 게시물을 작성한 유저가 동일 유저인지 확인
        if (isLogin)
            if (requestMember.getMemberId().equals(post.getMemberId())) {
            isAuthor = true;
        }

        List<Comment> commentList = commentRepository.findAllByPost(post);
        List<ResponseCommentDto> responseCommentDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            // 요청한 사용자와 댓글 작성자가 같다면 isCommenter = true
            boolean isCommenter = false;
            if (isLogin) {
                if (requestMember.getMemberId().equals(comment.getMember().getMemberId())) {
                    isCommenter = true;
                }
            }
            responseCommentDtoList.add(new ResponseCommentDto(comment, isCommenter));
        }

        List<String> tags = new ArrayList<>();

        // 게시물 태그 정보 가져오기 && List<String>형태로 변경
        List<PostTag> postTags = postTagRepository.findAllByPost(post);

        for (PostTag tag : postTags) {
            tags.add(tag.getTag().getTagName());
        }

        // 조회한 값들을 DTO로 변환 및 반환
        return mapper.toDetailDto(post, member, isAuthor, tags, responseCommentDtoList);
    }

    // 유저의 모든 게시물 조회하기
    @Transactional(readOnly = true)
    public List<PostSimpleResponseDto> getUserPostList(String nickname) {
        Member author = memberRepository.findByNickname(nickname).orElseThrow(
                MemberNotFoundException::new
        );
        List<Post> postList = postRepository.findAllByMemberId(author.getMemberId());
        List<PostSimpleResponseDto> response = new ArrayList<>();

        for (Post post : postList) {
            // 게시물 태그 정보 가져오기 && List<String>형태로 변경
            List<PostTag> postTags = postTagRepository.findAllByPost(post);
            List<String> tags = new ArrayList<>();
            for (PostTag tag : postTags) {
                tags.add(tag.getTag().getTagName());
            }
            response.add(mapper.toSimpleDto(post, author, tags, commentRepository.countByPost(post)));
        }

        return response;
    }

    // 멤버가 작성한 게시물을 태그로 조회
    @Transactional(readOnly = true)
    public List<PostSimpleResponseDto> getUserPostListByTag(String nickname, String tagName) {
        Member author = memberRepository.findByNickname(nickname).orElseThrow(
                MemberNotFoundException::new
        );

        Tag selectedTag = tagRepository.findByMemberAndTagName(author, tagName).orElseThrow(
                TagNotFoundException::new
        );

        List<PostTag> postTagList = postTagRepository.findAllByTag(selectedTag);

        List<PostSimpleResponseDto> response = new ArrayList<>();

        for (PostTag postTag : postTagList) {
            Optional<Post> post = postRepository.findById(postTag.getPost().getPostId());
            if (post.isPresent()) {
                Post selectedPost = post.get();

                List<PostTag> postTags = postTagRepository.findAllByPost(selectedPost);
                List<String> tags = new ArrayList<>();
                for (PostTag tag : postTags) {
                    tags.add(tag.getTag().getTagName());
                }

                response.add(mapper.toSimpleDto(selectedPost, author, tags, commentRepository.countByPost(selectedPost)));
            }
        }

        return response;
    }

    // 게시물 수정 페이지에 필요한 정보 조회
    @Transactional(readOnly = true)
    public PostInfoForUpdateDto getPostInfoForUpdate(Long postId, HttpServletRequest request) {

        Post post = postRepository.findById(postId).orElseThrow(
                PostNotFoundException::new
        );
        // 작성자 정보 가져오기
        Member member = memberRepository.findByMemberId(post.getMemberId());

        // 로그인 상태 확인
        String token = jwtUtil.resolveToken(request, "authorization");
        boolean isLogin = (token != null) ? true : false;

        Member requestMember = new Member();
        Claims claims = null;

        // jwt 내부 값 확인
        if (isLogin) {
            claims = jwtUtil.getUserInfoFromToken(token);

            // jwt 안의 정보를 이용하여 유저 조회
            Optional<Member> memberInJwt = memberRepository.findByLoginId(claims.getSubject());
            if (memberInJwt.isPresent()) {
                requestMember = memberInJwt.get();
            }
        }

        List<PostTag> postTags = postTagRepository.findAllByPost(post);
        List<String> tags = new ArrayList<>();
        for (PostTag tag : postTags) {
            tags.add(tag.getTag().getTagName());
        }

        return mapper.toInfoDto(post, member, tags);
    }
}
