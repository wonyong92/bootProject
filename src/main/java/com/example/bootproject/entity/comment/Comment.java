package com.example.bootproject.entity.comment;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.entity.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
public class Comment {


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", columnDefinition = "longtext")
    private String content;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Member writer;
}

