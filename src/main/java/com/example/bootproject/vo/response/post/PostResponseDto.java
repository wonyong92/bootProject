package com.example.bootproject.vo.response.post;

import com.example.bootproject.entity.post.Post;
import lombok.Data;

@Data
public class PostResponseDto {
    Integer postId;
    String title;
    String content;
    String writerId;
    Integer score;

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writerId = post.getWriter().getMemberId();
        this.score=post.getScore();
    }
}
