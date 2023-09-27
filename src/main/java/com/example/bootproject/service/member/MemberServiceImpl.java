package com.example.bootproject.service.member;

import com.example.bootproject.controller.LoginDto;
import com.example.bootproject.entity.member.Member;
import com.example.bootproject.repository.member.MemberRepository;
import com.example.bootproject.vo.MemberCreateDto;
import com.example.bootproject.vo.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        log.info("delete found : {}",findMember);
        if (findMember != null) {
            memberRepository.delete(findMember);
            return true;
        }
        return false;
    }

    @Override
    public Member doLogin(LoginDto dto) {
        Member findMember = memberRepository.findByIdAndPwd(dto.getId(),dto.getPwd());
        if(findMember != null){
            return findMember;
        }
        return null;
    }
}
