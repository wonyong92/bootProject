package com.example.bootproject.repository.comment;

import com.example.bootproject.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostPostId(Integer postId);

    List<Comment> findByWriterMemberId(String writerId);

    Optional<Comment> findByCommentIdAndWriter_MemberId(Long postId, String writerId);
}

