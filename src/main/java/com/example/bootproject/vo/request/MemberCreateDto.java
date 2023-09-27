package com.example.bootproject.vo.request;

import com.example.bootproject.entity.member.Member;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
//@Valid
public class MemberCreateDto {
    //@NotNull//blank는 허용
    @NotBlank String id;
    @NotBlank String name;
    @NotBlank String email;
    @NotBlank String pwd;

    public Member toEntity() {
        return new Member(id, name, email, pwd);
    }
}
