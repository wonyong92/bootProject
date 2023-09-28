package com.example.bootproject.service.comment;


import com.example.bootproject.entity.comment.Comment;
import com.example.bootproject.entity.post.Post;
import com.example.bootproject.repository.comment.CommentRepository;
import com.example.bootproject.repository.post.PostRepository;
import com.example.bootproject.vo.request.comment.CommentCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment createComment(CommentCreateDto dto,String writerId,Integer postId) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setWriterId(writerId);
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null)
        {
            return null;
        }
        comment.setPost(post);
        // Save the comment
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(CommentCreateDto dto, String writerId,Long id) {
        Comment existingComment = commentRepository.findByIdAndWriterId(id,writerId).orElse(null);
        if (existingComment != null) {
            existingComment.setContent(dto.getContent());
            return commentRepository.save(existingComment);
        }
        return null;
    }


    @Override
    public boolean deleteComment(Long id, String writerId) {
        Comment existingComment = commentRepository.findByIdAndWriterId(id,writerId).orElse(null);
        if (existingComment != null) {
            commentRepository.delete(existingComment);
            return true;
        }
        return false;
    }

    @Override
    public List<Comment> getCommentsByPostId(Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public List<Comment> getCommentsByWriterId(String writerId) {
        return commentRepository.findByWriterId(writerId);
    }
}

