package com.toy.practice.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberFindByIdRequest {
    @NotBlank(message = "아이디를 입력해주세요")
    private String id;
}
