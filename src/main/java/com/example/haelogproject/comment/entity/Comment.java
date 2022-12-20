package com.example.haelogproject.comment.entity;

import com.example.haelogproject.comment.dto.RequestCommentDto;
import com.example.haelogproject.common.TimeStamp;
import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.post.entity.Post;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(columnDefinition = "TEXT", nullable = true)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Comment(Post post, String content, Member member) {
        this.post = post;
        this.member = member;
        this.content = content;
    }

    public void update(String comment) {
        this.content = content;
    }
}
