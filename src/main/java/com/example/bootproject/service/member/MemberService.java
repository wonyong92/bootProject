package com.example.bootproject.service.member;

import com.example.bootproject.vo.LoginDto;
import com.example.bootproject.entity.member.Member;
import com.example.bootproject.vo.MemberCreateDto;
import com.example.bootproject.vo.MemberUpdateDto;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface MemberService {
    boolean memberCreate(MemberCreateDto dto);

    boolean memberUpdate(MemberUpdateDto dto);

    Member getMemberById(String id);

    boolean deleteMemberById(String id);

    Member doLogin(LoginDto dto);

    boolean logout(HttpSession session, HttpServletResponse resp);
}
