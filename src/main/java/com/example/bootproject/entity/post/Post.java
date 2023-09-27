package com.example.bootproject.entity.post;

import com.example.bootproject.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class Post {
    public Post(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    @Id
    Long id;
    String title;
    String content;

    @ManyToOne
    @JoinColumn(name = "id")
    Member writer;
}
