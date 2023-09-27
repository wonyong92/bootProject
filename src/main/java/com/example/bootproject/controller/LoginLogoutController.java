package com.example.bootproject.controller;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LoginLogoutController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@ModelAttribute LoginDto dto, HttpSession session){
        Member findMember  = memberService.doLogin(dto);
        if(findMember!=null){
            session.setAttribute("id", findMember.getId());
            session.setAttribute("loginInfo", findMember);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
