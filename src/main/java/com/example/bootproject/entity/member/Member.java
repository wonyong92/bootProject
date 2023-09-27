package com.example.bootproject.entity.member;

import com.example.bootproject.entity.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @OneToMany(mappedBy = "writer")
    List<Post> posts;

    public Member() {

    }

    public Member(String id, String name, String email, String pwd) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }
}
