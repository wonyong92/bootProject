package com.example.bootproject.entity.member;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class Member {
    @Id
    String id;
    String name;
    String email;
    String pwd;

    public Member() {

    }
}
