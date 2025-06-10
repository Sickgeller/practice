package com.toy.practice.member.model;

import com.toy.practice.board.model.Board;
import com.toy.practice.common.base.BaseEntity;
import com.toy.practice.common.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(
        name = "MEMBER"
)
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Member extends BaseEntity {

    /*
    * Member
    * */
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;
    @Column(name = "ID", nullable = false, unique = true, length = 30)
    private String id;
    @Column(name = "NAME", nullable = false, length = 8)
    private String name;
    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;
    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;
    @Builder.Default
    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active = true;
    @Builder.Default
    @Column(name = "IS_EMAIL_VERIFIED", nullable = false)
    private boolean emailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLETYPE", nullable = false)
    @Builder.Default
    private RoleType roletype = RoleType.USER;

    /*
    * Board
    * */
    @OneToMany
    @JoinColumn(name = "BOARD_ID")
    private List<Board> boardId;

    public void activate(){
        this.active = true;
    }

    public void deactivate(){
        this.active = false;
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
