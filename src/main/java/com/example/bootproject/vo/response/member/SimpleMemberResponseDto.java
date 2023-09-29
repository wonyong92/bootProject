package com.example.bootproject.vo.response.member;

import com.example.bootproject.entity.member.Member;
import lombok.Data;

@Data
public class SimpleMemberResponseDto {
    String id;
    String name;
    String email;
    String pwd;

    public SimpleMemberResponseDto(Member member) {
        this.id = member.getMemberId();
        this.email = member.getEmail();
        this.pwd = member.getPwd();
        this.name = member.getName();
    }


}
