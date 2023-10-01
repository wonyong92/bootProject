package com.example.bootproject.controller.rest.member;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.repository.member.MemberRepository;
import com.example.bootproject.service.member.MemberService;
import com.example.bootproject.vo.request.member.MemberCreateDto;
import com.example.bootproject.vo.request.member.MemberUpdateDto;
import com.example.bootproject.vo.response.member.SimpleMemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<Map<String, String>> testHandler(HttpSession session) {
        log.info("test controller");
        log.info(memberRepository.findAll().toString());
        log.info(session.getAttribute("id").toString());
        return ResponseEntity.ok(Map.of("test", "test"));
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<Void> createMember(@ModelAttribute @Valid MemberCreateDto dto, BindingResult result) throws Exception {
        log.info("member create request");
        log.info("body : {}", dto);

        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors:\n");
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            throw new Exception(errorMessage.toString());
        }

        memberService.memberCreate(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<Void> updateMember(@ModelAttribute MemberUpdateDto dto, BindingResult result, HttpSession session) throws Exception {
        log.info("member update request");
        log.info("body : {}", dto);
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors:\n");
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            throw new Exception(errorMessage.toString());
        }
        memberService.memberUpdate(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/profile")
    public String readMember(HttpSession session, Model model) {
        if(session!=null){
            String id = (String)session.getAttribute("id");
            Member member = memberService.getMemberById(id);
            log.info("{}",new SimpleMemberResponseDto(member));
            model.addAttribute("member",new SimpleMemberResponseDto(member));
            return "profile";
        }
        return "main";

    }

    @GetMapping("/delete")
    @ResponseBody
    public ResponseEntity<Void> deleteMember(@RequestParam String id) {
        boolean result = memberService.deleteMemberById(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
