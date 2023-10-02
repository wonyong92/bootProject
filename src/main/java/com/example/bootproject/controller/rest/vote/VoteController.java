package com.example.bootproject.controller.rest.vote;

import com.example.bootproject.service.vote.PostVoteService;
import com.example.bootproject.system.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
@Slf4j
public class VoteController {
    private final PostVoteService post_vote_service;

    @PostMapping
    public ResponseEntity vote(@RequestParam(defaultValue = "0") Integer vote, @RequestParam Integer postId, HttpSession session) {
        String id = SessionUtil.getLoginId(session);
        boolean result = post_vote_service.vote(vote, id, postId);
        log.info(String.valueOf(result));
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/score")
    public ResponseEntity<? extends Object> getScore(@RequestParam Integer postId){
        Integer score = post_vote_service.getScore(postId);
        return ResponseEntity.ok(score);
    }
}
