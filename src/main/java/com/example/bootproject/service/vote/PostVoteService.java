package com.example.bootproject.service.vote;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.entity.post.Post;
import com.example.bootproject.entity.vote.PostVote;
import com.example.bootproject.repository.member.MemberRepository;
import com.example.bootproject.repository.post.PostRepository;
import com.example.bootproject.repository.vote.PostVoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostVoteService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    private final PostVoteRepository postVoteRepository;

    public boolean vote(Integer vote, String memberId, Integer postId) {
        try {


            Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found."));

            Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found."));

            PostVote postVote = postVoteRepository.findByMemberMemberIdAndPostPostId(memberId, postId).or(() ->
            {
                PostVote postVote1 = new PostVote();
                postVote1.setPost(post);
                postVote1.setMember(member);
                postVote1.setScore(0);
                return java.util.Optional.of(postVoteRepository.save(postVote1));
            }).get();

            return processVote(postVote, post, member, vote);
        } catch (Exception e) {
            log.info("postVote create error");
            return false;
        }
    }

    private boolean processVote(PostVote postVote, Post post, Member member, Integer vote) {
        Integer score = postVote.getScore();
        if (score.equals(vote)) {
            log.info("Score not changed");
            return false;
        }

        if (score == 0) {
            return handleScoreZero(postVote, post, vote);
        } else if (score > 0) {
            return handlePositiveScore(postVote, post, vote);
        } else {
            return handleNegativeScore(postVote, post, vote);
        }
    }

    private boolean handleScoreZero(PostVote postVote, Post post, Integer vote) {
        if (vote > 0) {
            postVote.setScore(1);
            post.setScore(post.getScore() + 1);
            postRepository.save(post);
            postVoteRepository.save(postVote);
            log.info("Add score and current vote score = 1");
        } else {
            postVote.setScore(-1);
            post.setScore(post.getScore() - 1);
            postRepository.save(post);
            postVoteRepository.save(postVote);
            log.info("Subtract score and current vote score = -1");
        }
        return true;
    }

    private boolean handlePositiveScore(PostVote postVote, Post post, Integer vote) {
        if (vote < 0) {
            postVote.setScore(0);
            post.setScore(post.getScore() - 1);
            postRepository.save(post);
            postVoteRepository.save(postVote);
            log.info("Subtract score and current vote score = 0");
            return true;
        } else {
            log.info("nothing changed");
            return false;
        }
    }

    private boolean handleNegativeScore(PostVote postVote, Post post, Integer vote) {
        if (vote < 0) {
            log.info("Vote error: Invalid vote");
            return false;
        } else {
            postVote.setScore(0);
            post.setScore(post.getScore() + 1);
            postRepository.save(post);
            postVoteRepository.save(postVote);
            log.info("Add score and current vote score = 0");
            return true;
        }
    }
}

