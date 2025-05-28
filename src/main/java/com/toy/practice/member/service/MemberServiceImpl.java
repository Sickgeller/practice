package com.toy.practice.member.service;

import com.toy.practice.common.exception.BusinessException;
import com.toy.practice.common.exception.ErrorCode;
import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.model.Member;
import com.toy.practice.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long register(String id, String name, String email, String password) {
        validateDuplicateMember(id, email);
        Member member = Member.createMember(id, name, password, email);
        return memberRepository.save(member).getMemberId();
    }

    @Override
    @Transactional
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> MemberException.memberNotFound(memberId));
    }

    @Override
    @Transactional
    public Member update(Long memberId, String name, String email) {
        Member member = findById(memberId);
        
        // 이메일 중복 체크 (다른 회원의 이메일과 중복되는지)
        if (!member.getEmail().equals(email) && memberRepository.existsByEmail(email)) {
            throw MemberException.duplicatedMemberEmail();
        }

        member.update(name, email);
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public void delete(Long memberId) {
        Member member = findById(memberId);
        memberRepository.delete(member);
    }

    @Override
    @Transactional
    public void changePassword(Long memberId, String oldPassword, String newPassword) {
        Member member = findById(memberId);
        member.changePassword(newPassword);
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void activate(Long memberId) {
        Member member = findById(memberId);
        member.activate();
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void deactivacte(Long memberId) {
        Member member = findById(memberId);
        member.deactivate();
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public Member login(String id, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> MemberException.loginFailed());

        if (!member.getPassword().equals(password)) {
            throw MemberException.loginFailed();
        }

        return member;
    }

    private void validateDuplicateMember(String id, String email) {
        if(memberRepository.existsById(id)){
            throw MemberException.duplicatedMemberId();
        }
        if(memberRepository.existsByEmail(email)){
            throw MemberException.duplicatedMemberEmail();
        }
    }
}
