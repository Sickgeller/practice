package com.toy.practice.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {

    @Getter
    @Builder
    public static class SignUp {
        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "아이디는 4~12자의 영문자와 숫자만 사용 가능합니다.")
        private String id;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$", 
                message = "비밀번호는 8~20자의 영문자, 숫자, 특수문자를 포함해야 합니다.")
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        @Pattern(regexp = "^[가-힣]{2,8}$", message = "이름은 2~8자의 한글만 사용 가능합니다.")
        private String name;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;
    }

    @Getter
    @Setter
    public static class Login {
        @NotBlank(message = "아이디를 입력해주세요")
        private String id;

        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Update {
        @NotBlank
        @Size(min = 2, max = 8, message = "이름은 2자이상 8자 이하로 입력해주세요")
        private String name;

        @NotBlank
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @Size(max = 100, message = "이메일은 100자 이내로 작성해주세요")
        private String email;
    }
}
