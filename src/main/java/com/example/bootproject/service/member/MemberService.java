package com.example.bootproject.service.member;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.vo.request.login.LoginRequestDto;
import com.example.bootproject.vo.request.member.MemberCreateDto;
import com.example.bootproject.vo.request.member.MemberUpdateDto;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface MemberService {
    boolean memberCreate(MemberCreateDto dto);

    boolean memberUpdate(MemberUpdateDto dto);

    Member getMemberById(String id);

    boolean deleteMemberById(String id);

    Member doLogin(LoginRequestDto dto);

    boolean logout(HttpSession session, HttpServletResponse resp);
}
