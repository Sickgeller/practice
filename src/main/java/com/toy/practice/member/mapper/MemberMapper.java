package com.toy.practice.member.mapper;

import com.toy.practice.member.dto.MemberLoginRequest;
import com.toy.practice.member.dto.MemberResponse;
import com.toy.practice.member.dto.MemberSignUpRequest;
import com.toy.practice.member.dto.MemberUpdateRequest;
import com.toy.practice.member.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberResponse toDto(Member member);
    Member toEntity(MemberSignUpRequest request);
    Member toEntity(MemberLoginRequest request);
    Member toEntity(MemberUpdateRequest request);
}

