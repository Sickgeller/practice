package com.toy.practice.member.service;

import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.model.Member;
import com.toy.practice.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(Member member) {
        log.info("회원가입 시작 - ID: {}", member.getId());
        validateIdDuplicate(member.getId());
        validateEmailDuplicate(member.getEmail());
        
        log.debug("비밀번호 인코딩 시작");
        String newPassword = passwordEncoder.encode(member.getPassword());
        member.changePassword(newPassword);
        log.debug("비밀번호 인코딩 완료");
        
        memberRepository.save(member);
        log.info("회원가입 완료 - ID: {}", member.getId());
    }

    private void validateIdDuplicate(String id) {
        log.debug("ID 중복 체크 - ID: {}", id);
        if(memberRepository.existsById(id)) {
            log.warn("ID 중복 발견 - ID: {}", id);
            throw MemberException.duplicatedMemberId();
        }
    }

    private void validateEmailDuplicate(String email) {
        log.debug("이메일 중복 체크 - Email: {}", email);
        if(memberRepository.existsByEmail(email)){
            log.warn("이메일 중복 발견 - Email: {}", email);
            throw MemberException.duplicatedMemberEmail();
        }
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
    public Member update(Member member) {
        Member foundMember = findById(member.getMemberId());
        // 이메일 중복 체크 (다른 회원의 이메일과 중복되는지)
        if (!member.getEmail().equals(foundMember.getEmail()) && memberRepository.existsByEmail(member.getEmail())) {
            throw MemberException.duplicatedMemberEmail();
        }
        foundMember.update(member.getName(), member.getEmail());
        return foundMember;
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

        if(!passwordEncoder.matches(oldPassword, member.getPassword())){
            throw MemberException.invalidPassword();
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        member.changePassword(encodedNewPassword);
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
    public Member login(Member member) {
        log.info("로그인 시도 - ID: {}", member.getId());
        Member foundMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> {
                    log.warn("로그인 실패 - 존재하지 않는 ID: {}", member.getId());
                    return MemberException.loginFailed();
                });
        
        if (!passwordEncoder.matches(member.getPassword(), foundMember.getPassword())) {
            log.warn("로그인 실패 - 비밀번호 불일치 - ID: {}", member.getId());
            throw MemberException.loginFailed();
        }
        log.info("로그인 성공 - ID: {}", member.getId());
        return foundMember;
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(String id) {
        return memberRepository.existsById(id);
    }

    @Override
    public boolean existsById(Long memberId) {
        return memberRepository.existsById(memberId);
    }

}
