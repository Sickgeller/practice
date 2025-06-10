package com.toy.practice.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {
    @NotBlank
    @Size(min = 2, max = 8, message = "이름은 2자이상 8자 이하로 입력해주세요")
    private String name;

    @NotBlank
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Size(max = 100, message = "이메일은 100자 이내로 작성해주세요")
    private String email;
}
