package com.example.haelogproject.post.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(nullable = false)
    private String tagName;

    @Builder
    public Tag(String tagName, Member member) {
        this.tagName = tagName;
        this.member = member;
    }
}