package com.example.bootproject.service.post;

import com.example.bootproject.entity.post.Post;
import com.example.bootproject.vo.request.postCreateDto;
import com.example.bootproject.vo.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    long createPost(postCreateDto dto, String id);

    Post getPost(Integer postId);

    Page<PostResponseDto> findAll(Pageable pageable);
}
