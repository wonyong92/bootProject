package com.example.bootproject.repository.post;

import com.example.bootproject.entity.post.Post;
import com.example.bootproject.vo.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findById(Integer id);

    Page<Post> findAll(Pageable pageable);

    default Page<PostResponseDto> findAllDto(Pageable pageable){
        Page<Post> page = findAll(pageable);
        Page<PostResponseDto> dtoPage = page.map(content -> new PostResponseDto(content));
        return dtoPage;
    };
    default Page<PostResponseDto> findAllDtoByMemberId(Pageable pageable,String memberId){
        Page<Post> page = findByWriterId(pageable,memberId);
        Page<PostResponseDto> dtoPage = page.map(content -> new PostResponseDto(content));
        return dtoPage;
    }

    Page<Post> findByWriterId(Pageable pageable,String writerId);

}
