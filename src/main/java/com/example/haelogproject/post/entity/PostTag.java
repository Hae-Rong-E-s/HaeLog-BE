package com.example.haelogproject.post.entity;

import com.example.haelogproject.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @Builder
    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }
}
