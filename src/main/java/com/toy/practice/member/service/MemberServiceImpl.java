package com.toy.practice.member.service;

import com.toy.practice.member.dto.MemberRequest;
import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.model.Member;
import com.toy.practice.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long register(String id, String name, String email, String password) {
        String newPassword = passwordEncoder.encode(password);
        validateDuplicateMember(id, email);
        Member member = Member.createMember(id, name, newPassword, email);
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
                .orElseThrow(MemberException::memberNotFound);
    }

    @Override
    @Transactional
    public Member findById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberException::memberNotFound);

    }

    @Override
    @Transactional
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberException::memberNotFound);
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

        String EncodedOldPassword = passwordEncoder.encode(oldPassword);
        String EncodedNewPassword = passwordEncoder.encode(newPassword);

        if(!passwordEncoder.matches(oldPassword, member.getPassword())){
            throw MemberException.invalidPassword();
        }
        member.changePassword(EncodedNewPassword);
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
    public Member login(MemberRequest.Login request) {
        Member member = memberRepository.findById(request.getId())
                .orElseThrow(MemberException::loginFailed);
        
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw MemberException.loginFailed();
        }
        
        return member;
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(String id) {
        return memberRepository.existsById(id);
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
