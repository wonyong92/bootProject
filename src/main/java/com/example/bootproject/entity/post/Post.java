package com.example.bootproject.entity.post;

import com.example.bootproject.entity.comment.Comment;
import com.example.bootproject.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer postId;
    String title;
    @Lob//text, clob, blob 과 같이 긴 문자열 데이터에 대해 String 맵핑 시 해당 어노테이션 필요
    String content;


    @JsonBackReference
    @ManyToOne(targetEntity = Member.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "writer_id")
    Member writer;

    @JsonBackReference
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)

    List<Comment> comments;
    @OneToOne
    @JoinColumn(name="parent_id")
    Post parent;
    Integer score;
    private String file1;
    private String file2;

    public Post(String title, String content, Member member, Post parent) {
        this.title = title;
        this.content = content;
        this.writer = member;
        this.parent = parent;
        this.score = 0;
    }

    public Post(Integer postId, String title, String content, Member member, Post parent) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writer = member;
        this.parent = parent;
        this.score = 0;
    }
}
