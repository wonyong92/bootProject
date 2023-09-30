package com.example.bootproject.controller.rest.loginlogout;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.service.member.MemberService;
import com.example.bootproject.vo.request.login.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginLogoutController {

    private final MemberService memberService;

    @PostMapping("/login")
    //form이 아닌 fetch 기반 로그인 사용
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto dto, HttpSession session) {
        Member findMember = memberService.doLogin(dto);
        log.info("login Request {} ",dto);
        if (findMember != null) {
            session.setAttribute("id", findMember.getMemberId());
//            session.setAttribute("loginInfo", findMember);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session, HttpServletResponse resp) {
        boolean result = memberService.logout(session, resp);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
