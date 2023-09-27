package com.example.bootproject.service.post;

import com.example.bootproject.controller.post.postCreateDto;
import com.example.bootproject.entity.member.Member;
import com.example.bootproject.entity.post.Post;
import com.example.bootproject.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public long createPost(postCreateDto dto, String id) {
        Member member = memberRepository.findById(id).get();
        if(member!=null){
            Post entity = dto.dtoToEntity(member);
            postRepository.save(entity);
            return entity.getId();
        }
        return -1;

    }
}
