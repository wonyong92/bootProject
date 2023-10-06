package com.example.bootproject.repository.vote;

import com.example.bootproject.entity.vote.PostVote;
import com.example.bootproject.entity.vote.PostVoteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostVoteRepository extends JpaRepository<PostVote, PostVoteId> {
    Optional<PostVote> findByMemberMemberIdAndPostPostId(String member_id, Integer post_id);

    void deleteByPost_PostId(Integer postId);
}
