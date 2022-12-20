package com.example.haelogproject.post.entity;

import com.example.haelogproject.comment.entity.Comment;
import javax.persistence.*;

import com.example.haelogproject.common.TimeStamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "Post")
public class Post extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false, unique = true)
    private Long memberId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String content;

    @Column(length = 150, nullable = true)
    private String contentSummary;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post(Long memberId, String title, String content, String contentSummary){
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.contentSummary = contentSummary;
    }

    // 연관관계 편의 메소드
    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
}
