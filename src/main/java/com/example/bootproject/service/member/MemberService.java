package com.example.bootproject.service.member;

import com.example.bootproject.vo.MemberCreateDto;
import com.example.bootproject.vo.MemberUpdateDto;
import org.springframework.stereotype.Service;

public interface MemberService {
    boolean memberCreate(MemberCreateDto dto);

    boolean memberUpdate(MemberUpdateDto dto);
}
