package com.example.bootproject.repository.member;

import com.example.bootproject.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Member findByIdAndPwd(String id, String pwd);
}
