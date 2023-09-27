package com.example.bootproject.service.post;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.entity.post.Post;
import com.example.bootproject.repository.member.MemberRepository;
import com.example.bootproject.repository.post.PostRepository;
import com.example.bootproject.vo.request.postCreateDto;
import com.example.bootproject.vo.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public long createPost(postCreateDto dto, String id) {
        Member member = memberRepository.findById(id).get();
        if (member != null) {
            Post entity = dto.dtoToEntity(member);
            postRepository.save(entity);
            return entity.getId();
        }
        return -1;

    }

    @Override
    public Post getPost(Integer postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public Page<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAllDto(pageable);
    }
}
