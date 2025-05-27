package com.toy.practice.member.service;

import com.toy.practice.common.exception.BusinessException;
import com.toy.practice.common.exception.ErrorCode;
import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.model.Member;
import com.toy.practice.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 서비스 계층에서

    @Override
    @Transactional
    public Long register(String id, String name, String email, String password) {
        validateDuplicateMember(id, email);
        Member member = Member.createMember(id, name, email, password);
        return memberRepository.save(member).getMemberId();
    }

    @Override
    @Transactional
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).get();
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

    private void validateDuplicateMember(String id, String email) {
        if(memberRepository.existsById(id)){
            MemberException.duplicatedMemberId();
        }
        if(memberRepository.existsByEmail(email)){
            MemberException.duplicatedMemberEmail();
        }
    }
}
