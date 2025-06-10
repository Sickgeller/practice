package com.toy.practice.member.dto;

import com.toy.practice.common.enums.RoleType;
import com.toy.practice.member.model.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private Long memberId;
    private String id;
    private String email;
    private String name;
    private Boolean active;
    private Boolean emailVerified;
    private RoleType role;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public static MemberResponse of(Member member){
        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .active(member.isActive())
                .emailVerified(member.isEmailVerified())
                .createdAt(member.getCreatedAt())
                .updateAt(member.getUpdateAt())
                .role(member.getRoletype())
                .build();
    }

}
