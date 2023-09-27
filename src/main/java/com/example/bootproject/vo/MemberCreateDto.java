package com.example.bootproject.vo;

import com.example.bootproject.entity.member.Member;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class MemberCreateDto {
    @NotNull
    String id;
    @NotNull
    String name;
    @NotNull
    String email;
    @NotNull
    String pwd;

    public Member toEntity(){
        return new Member(id,name,email,pwd);
    };
}
