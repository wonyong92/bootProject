package com.example.bootproject.controller.post;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.entity.post.Post;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class postCreateDto {
    @NotBlank
    String title;
    String content;

    public Post dtoToEntity(Member member){
        return new Post(title,content,member);
    }
}
