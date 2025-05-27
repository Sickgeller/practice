package com.toy.practice.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {

    public static class SignUp{

        @NotBlank
        @Size(min = 5, max = 20, message = "아이디는 5자이상 20자이하로 입력해주세요")
        private String id;

        @NotBlank
        @Size(min = 2, max = 8, message = "이름은 2자이상 8자 이하로 입력해주세요")
        private String name;

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z][a-zA-Z\\d@$!%*#?&]){8,20}",
                message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 최소 한번씩 포함해서 8자이상 20자이하로 작성해주세요"
        )
        private String password;

        @NotBlank
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @Size(max = 100, message = "이메일은 100자 이내로 작성해주세요")
        private String email;
    }

}
