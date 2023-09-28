package com.example.bootproject.entity.member;

import com.example.bootproject.entity.comment.Comment;
import com.example.bootproject.entity.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Member {
    @Id
    String id;
    String name;
    String email;
    String pwd;

    // @JsonBackReference//해당 필드는 더이상 직렬화하지 않음 -> toString() 도중 발생하는 세션 종료 문제에 대해서는 유효하지 않다
    // toString() 수행 도중 프록시 로딩에 필요한 세션이 종료되는 문제 발생
    // 직렬화 도중 발생하는 무한 루프 발생 문제를 해결하기 위하여 사용하는 어노테이션
    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonBackReference
    List<Post> posts;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonBackReference
    List<Comment> comments;

    public Member() {

    }

    public Member(String id, String name, String email, String pwd) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }
}
