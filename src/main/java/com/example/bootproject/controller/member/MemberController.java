package com.example.bootproject.controller.member;

import com.example.bootproject.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    @GetMapping("/member")
    public ResponseEntity<Map<String,String>> testHandler(){
        log.info("test controller");
        log.info(memberRepository.findAll().toString());
        return ResponseEntity.ok(Map.of("test","test"));
    }

}
