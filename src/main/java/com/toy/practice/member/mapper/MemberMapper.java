package com.toy.practice.member.mapper;

import com.toy.practice.member.dto.*;
import com.toy.practice.member.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    MemberResponse toDto(Member member);
    Member toEntityFromSignUp(MemberSignUpRequest request);
    Member toEntityFromLogin(MemberLoginRequest request);
    Member toEntityFromUpdate(MemberUpdateRequest request);
}

