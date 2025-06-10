package com.toy.practice.member.appservice;

import com.toy.practice.member.dto.MemberLoginRequest;
import com.toy.practice.member.dto.MemberResponse;
import com.toy.practice.member.dto.MemberSignUpRequest;
import com.toy.practice.member.dto.MemberUpdateRequest;
import com.toy.practice.member.mapper.MemberMapper;
import com.toy.practice.member.model.Member;
import com.toy.practice.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MemberAppServiceImpl implements MemberAppService {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<MemberResponse> getAllMembers() {
        log.info("전체 회원 목록 조회");
        return memberService.findAll().stream()
                .map(memberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void register(MemberSignUpRequest request) {
        log.info("회원가입 요청 - ID: {}", request.getId());
        Member member = memberMapper.toEntity(request);
        log.debug("DTO -> Entity 변환 완료");
        memberService.register(member);
        log.info("회원가입 처리 완료 - ID: {}", request.getId());
    }

    @Override
    public MemberResponse login(MemberLoginRequest request) {
        log.info("로그인 요청 - ID: {}", request.getId());
        Member tryToLoginMember = memberMapper.toEntity(request);
        log.debug("DTO -> Entity 변환 완료");
        Member loginMember = memberService.login(tryToLoginMember);
        log.info("로그인 성공 - ID: {}", request.getId());
        return memberMapper.toDto(loginMember);
    }

    @Override
    public MemberResponse update(MemberUpdateRequest request) {
        log.info("회원 정보 수정 요청");
        Member tryToUpdateMember = memberMapper.toEntity(request);
        log.debug("DTO -> Entity 변환 완료");
        Member member = memberService.update(tryToUpdateMember);
        log.info("회원 정보 수정 완료");
        return memberMapper.toDto(member);
    }

    @Override
    public MemberResponse findById(String id) {
        log.info("회원 조회 요청 - ID: {}", id);
        Member foundMember = memberService.findById(id);
        log.info("회원 조회 완료 - ID: {}", id);
        return memberMapper.toDto(foundMember);
    }
}
