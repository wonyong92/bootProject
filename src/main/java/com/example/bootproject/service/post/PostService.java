package com.example.bootproject.service.post;

import com.example.bootproject.entity.post.Post;
import com.example.bootproject.vo.request.postCreateDto;

public interface PostService {
    long createPost(postCreateDto dto, String id);

    Post getPost(Integer postId);
}
