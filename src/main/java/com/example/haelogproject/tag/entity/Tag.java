package com.example.haelogproject.tag.entity;

import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.post.entity.Post;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = false)
    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Tag(String tagName, Post post, Member member) {
        this.tagName = tagName;
        this.post = post;
        this.member = member;
    }
}
