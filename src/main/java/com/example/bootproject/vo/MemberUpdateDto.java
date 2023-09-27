package com.example.bootproject.vo;

import com.example.bootproject.entity.member.Member;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class MemberUpdateDto {
    String id;
    String name;
    String email;

    public Member updateEntity(Member entity){
        entity.setEmail(this.getEmail() != null ? this.getEmail() : entity.getEmail());
        entity.setName(this.getName() != null ? this.getName() : entity.getName());
        return entity;
    }
}
