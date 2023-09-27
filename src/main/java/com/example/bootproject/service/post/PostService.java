package com.example.bootproject.service.post;

import com.example.bootproject.controller.post.postCreateDto;
import org.springframework.stereotype.Service;

public interface PostService {
    public long createPost(postCreateDto dto, String id) ;
}
