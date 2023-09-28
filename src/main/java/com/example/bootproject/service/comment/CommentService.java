package com.example.bootproject.service.comment;

import com.example.bootproject.entity.comment.Comment;
import com.example.bootproject.vo.request.comment.CommentCreateDto;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    Comment getCommentById(Long id);

    Comment createComment(CommentCreateDto comment, String writerId, Integer postId);

    Comment updateComment(CommentCreateDto dto, String writerId, Long id);

    boolean deleteComment(Long id, String writerId);

    List<Comment> getCommentsByPostId(Integer postId);

    List<Comment> getCommentsByWriterId(String writerId);
}

