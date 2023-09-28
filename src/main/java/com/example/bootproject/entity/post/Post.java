package com.example.bootproject.entity.post;

import com.example.bootproject.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String title;
    @Lob//text, clob, blob 과 같이 긴 문자열 데이터에 대해 String 맵핑 시 해당 어노테이션 필요
    String content;


    @JsonBackReference
    @ManyToOne(targetEntity = Member.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "writer_id")
    Member writer;

    public Post(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Post(Integer postId, String title, String content, Member member) {
        this(title, content, member);
        this.id = postId;
    }
}
