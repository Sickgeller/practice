package com.toy.practice.member.dto;

import com.toy.practice.member.model.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemberResponse {

    private Long memberId;
    private String id;
    private String email;
    private String name;
    private Boolean active;
    private Boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public static MemberResponse from(Member member){
            return MemberResponse.builder()
                    .memberId(member.getMemberId())
                    .id(member.getId())
                    .name(member.getName())
                    .email(member.getEmail())
                    .active(member.isActive())
                    .emailVerified(member.isEmailVerified())
                    .createdAt(member.getCreatedAt())
                    .updateAt(member.getUpdateAt())
                    .build();
    }

}
