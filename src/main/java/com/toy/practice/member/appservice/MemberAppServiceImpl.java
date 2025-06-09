package com.toy.practice.member.appservice;

import com.toy.practice.member.dto.*;
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
        Member member = memberMapper.toEntityFromSignUp(request);
        log.debug("DTO -> Entity 변환 완료");
        memberService.register(member);
        log.info("회원가입 처리 완료 - ID: {}", request.getId());
    }

    @Override
    public MemberResponse update(MemberUpdateRequest request) {
        log.info("회원 정보 수정 요청");
        Member tryToUpdateMember = memberMapper.toEntityFromUpdate(request);
        log.debug("DTO -> Entity 변환 완료");
        Member member = memberService.update(tryToUpdateMember);
        log.info("회원 정보 수정 완료");
        return memberMapper.toDto(member);
    }

    @Override
    public MemberResponse findById(MemberFindByIdRequest request) {
        log.info("회원 조회 요청 - ID: {}", request.getId());
        Member foundMember = memberService.findById(request.getId());
        log.info("회원 조회 완료 - ID: {}", request.getId());
        return memberMapper.toDto(foundMember);
    }

    @Override
    public void deleteMember(MemberDeleteRequest request) {

    }
}
