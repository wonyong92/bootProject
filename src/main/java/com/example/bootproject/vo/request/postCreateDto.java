package com.example.bootproject.vo.request;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.entity.post.Post;
import lombok.Data;

@Data
public class postCreateDto {
    String title;

    String content;

    public Post dtoToEntity(Member member) {
        return new Post(title, content, member);
    }

    public Post dtoToEntity(Member member,Integer postId) {
        return new Post(postId,title, content, member);
    }
}
