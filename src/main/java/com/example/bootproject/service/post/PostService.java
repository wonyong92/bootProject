package com.example.bootproject.service.post;

import com.example.bootproject.entity.post.Post;
import com.example.bootproject.vo.request.post.postCreateDto;
import com.example.bootproject.vo.response.post.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    long createPost(postCreateDto dto, String id);

    Post getPost(Integer postId);

    Page<PostResponseDto> findAll(Pageable pageable);

    long updatePost(postCreateDto dto, String id, Integer postId);

    Page<PostResponseDto> findAllByMemberId(Pageable pageable, String memberId);

    boolean deletePost(String id, Integer postId);
}
