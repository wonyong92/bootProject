package com.example.bootproject.service.member;

import com.example.bootproject.repository.member.MemberRepository;
import com.example.bootproject.vo.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public boolean memberCreate(MemberCreateDto dto) {
        memberRepository.save(dto.toEntity());
        return false;
    }
}
