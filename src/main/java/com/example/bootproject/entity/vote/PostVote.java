package com.example.bootproject.entity.vote;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.entity.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(PostVoteId.class) // Specify the composite key class
@Table(name = "postvote")
public class PostVote {

//    @Id
//    @Column(name="member_id")
//    private String memberId;
//
//    @Id
//    @Column(name="post_id")
//    private Integer postId;

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "score")
    private Integer score = 0;

    // Constructors, other properties, getters, setters
}

