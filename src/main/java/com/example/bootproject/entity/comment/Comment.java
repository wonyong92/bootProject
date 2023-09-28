package com.example.bootproject.entity.comment;

import com.example.bootproject.entity.post.Post;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @OneToOne
    @JoinColumn(name = "post_id")
    Post post;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "content", columnDefinition = "longtext")
    private String content;
    @Column(name = "writer_id")
    private String writerId;

    // Getter and Setter methods (omitted for brevity)

    // Constructors (omitted for brevity)
}
