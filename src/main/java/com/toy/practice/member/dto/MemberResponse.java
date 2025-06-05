package com.toy.practice.member.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
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
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}
