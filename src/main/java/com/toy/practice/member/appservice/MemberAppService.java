package com.toy.practice.member.appservice;

import com.toy.practice.member.dto.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public interface MemberAppService {
    List<MemberResponse> getAllMembers();

    void register(MemberSignUpRequest request);

    MemberResponse update(MemberUpdateRequest request);

    MemberResponse findById(MemberFindByIdRequest request);

    void deleteMember(MemberDeleteRequest request);
}
