package com.example.bootproject.vo.response;

import com.example.bootproject.entity.post.Post;
import lombok.Data;

@Data
public class PostResponseDto {
    String title;
    String content;
    String writerId;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writerId = post.getWriter().getId();
    }
}
