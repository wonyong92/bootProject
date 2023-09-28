package com.example.bootproject.vo.response.comment;

import com.example.bootproject.entity.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseDto {
    Long commentId;
    String content;
    String writerId;

    public CommentResponseDto(Comment ent) {
        this(ent.getId(), ent.getContent(), ent.getWriter().getId());
    }
}
