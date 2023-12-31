package com.example.bootproject.repository.post;

import com.example.bootproject.entity.post.Post;
import com.example.bootproject.vo.response.post.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findById(Integer id);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByParentIdIsNull(Pageable pageable);

    Page<Post> findByWriterIdAndParentIdIsNull(String memberId, Pageable pageable);

    Page<Post> findByWriterId(String memberId, Pageable pageable);

    default Page<PostResponseDto> findAllDto(Pageable pageable) {
        Page<Post> page = findByParentIdIsNull(pageable);
        Page<PostResponseDto> dtoPage = page.map(content -> new PostResponseDto(content));
        return dtoPage;
    }

    default Page<PostResponseDto> findAllDtoByMemberId(Pageable pageable, String memberId) {
        Page<Post> page = findByWriterIdAndParentIdIsNull(memberId, pageable);
        Page<PostResponseDto> dtoPage = page.map(content -> new PostResponseDto(content));
        return dtoPage;
    }

    Page<Post> findByWriterId(Pageable pageable, String writerId);

    Optional<Post> findByIdAndWriterId(Integer postId, String writerId);

    default boolean deleteByIdAndCheckSuc(Integer postId) {
        deleteById(postId);
        return findById(postId).orElse(null) == null;
    }

    List<Post> findByParentId(Integer parentId);


    default Page<PostResponseDto> titleSearch(String input, Pageable pageable) {
        Page<Post> page = findPostsByTitle(input, pageable);
        Page<PostResponseDto> dtoPage = page.map(content -> new PostResponseDto(content));
        return dtoPage;
    }

    @Query(value = "SELECT * FROM post WHERE MATCH(title) AGAINST(CONCAT('*', :input, '*') IN BOOLEAN MODE) ORDER BY MATCH(title) AGAINST(CONCAT('*', :input, '*') IN BOOLEAN MODE) DESC", nativeQuery = true)
    Page<Post> findPostsByTitle(@Param("input") String input, Pageable pageable);
}
