package com.example.bootproject.vo.response.post;

import com.example.bootproject.entity.post.Post;
import lombok.Data;

@Data
public class PostResponseDto {
    Integer id;
    String title;
    String content;
    String writerId;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writerId = post.getWriter().getId();
    }
}
