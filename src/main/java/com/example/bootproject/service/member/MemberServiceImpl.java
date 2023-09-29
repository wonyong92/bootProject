package com.example.bootproject.service.member;

import com.example.bootproject.entity.member.Member;
import com.example.bootproject.repository.comment.CommentRepository;
import com.example.bootproject.repository.member.MemberRepository;
import com.example.bootproject.repository.post.PostRepository;
import com.example.bootproject.vo.request.login.LoginRequestDto;
import com.example.bootproject.vo.request.member.MemberCreateDto;
import com.example.bootproject.vo.request.member.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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

    //    @Override
//    public boolean deleteMemberById(String id) {
//        Member findMember = memberRepository.findById(id).orElse(null);
//        log.info("delete found : {}", new SimpleMemberResponseDto(findMember));
//        if (findMember != null) {
//            // 먼저 관련된 포스트와 해당 포스트의 댓글 삭제
//            findMember.getPosts().forEach(post -> {
//                //post.setWriter(null);  // 부모 엔티티 참조 해제
//                post.getComments().forEach(comment -> {
//                    //commentRepository.save(comment);
//                    commentRepository.delete(comment);
//                });
//                //postRepository.save(post);
//                postRepository.delete(post);
//            });
//            //memberRepository.delete(findMember);
//            return true;
//        }
//        return false;
//    }
    @Override
    public boolean deleteMemberById(String id) {
        Member findMember = memberRepository.findById(id).orElse(null);
        log.info("delete found : {}", findMember);
        if (findMember != null) {
            memberRepository.delete(findMember);
            return true;
        }
        return false;
    }

    @Override
    public Member doLogin(LoginRequestDto dto) {
        Member findMember = memberRepository.findByMemberIdAndPwd(dto.getId(), dto.getPwd());
        return findMember;
    }

    @Override
    public boolean logout(HttpSession session, HttpServletResponse resp) {
        if (session.getAttribute("id") != null) {
            session.invalidate();
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            return true;
        }
        return false;
    }
}
