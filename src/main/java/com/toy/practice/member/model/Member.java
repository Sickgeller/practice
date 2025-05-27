package com.toy.practice.member.model;

import com.toy.practice.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "MEMBER"
)
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1, allocationSize = 255
)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "MEMBER_SEQ")
    @Column(name = "MEMBER_ID" , length = 255, nullable = false)
    private Long memberId;
    @Column(name = "ID", nullable = false, unique = true, length = 30)
    private String id;
    @Column(name = "NAME", nullable = false, length = 3)
    private String name;
    @Column(name = "PASSWORD", nullable = false, length = 20)
    private String password;
    @Column(name = "EMAIL", nullable = false, length = 30, unique = true)
    private String email;
    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active = true;
    @Column(name = "IS_EMAIL_VERIFIED", nullable = false)
    private boolean emailVerified = false;

    @Builder
    public static Member createMember(String id, String name, String password, String email) {
        return Member.builder()
                .id(id)
                .name(name)
                .password(password)
                .email(email)
                .build();
    }
    public void activate(){
        this.active = true;
    }

    public void deactivate(){
        this.active = false;
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
    }
}
