package com.example.bootproject.entity.vote;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.entity.post.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


@Data
@NoArgsConstructor
public class PostVoteId implements Serializable {

    @EqualsAndHashCode.Include
    private String member;
    @EqualsAndHashCode.Include
    private Integer post;
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        PostVoteId that = (PostVoteId) o;
//        return Objects.equals(getMember(), that.getMember()) &&
//                Objects.equals(getPost(), that.getPost());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getMember(), getPost());
//    } // 롬복으로 처리
}