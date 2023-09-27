package com.example.bootproject.service.member;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.repository.member.MemberRepository;
import com.example.bootproject.vo.request.LoginRequestDto;
import com.example.bootproject.vo.request.MemberCreateDto;
import com.example.bootproject.vo.request.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public boolean memberCreate(MemberCreateDto dto) {
        try {
            memberRepository.save(dto.toEntity());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean memberUpdate(MemberUpdateDto dto) {
        Member findMember = memberRepository.findById(dto.getId()).orElse(null);
        log.info("found: {}", findMember);
        if (findMember != null) {
            memberRepository.save(dto.updateEntity(findMember));
            return true;
        }
        return false;
    }

    @Override
    public Member getMemberById(String id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteMemberById(String id) {
        Member findMember = memberRepository.findById(id).orElse(null);
        log.info("delete found : {}", findMember);
        if (findMember != null) {
            memberRepository.delete(findMember);
            return true;
        }
        return false;
    }

    @Override
    public Member doLogin(LoginRequestDto dto) {
        Member findMember = memberRepository.findByIdAndPwd(dto.getId(), dto.getPwd());
        return findMember;
    }

    @Override
    public boolean logout(HttpSession session, HttpServletResponse resp) {
        if (session.getAttribute("id") != null) {
            session.invalidate();
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            return true;
        }
        return false;
    }
}
