package com.toy.practice.board.model;

import com.toy.practice.common.base.BaseEntity;
import com.toy.practice.member.model.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@Table(name = "BOARD")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "BOARD_SEQ_GENERATOR", sequenceName = "BOARD_SEQ")
public class Board extends BaseEntity {

    /**
     * Board
     */
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "BOARD_ID")
    private Long boardId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Builder.Default
    @Column(name = "STATUS")
    private int status = 1;

    /**
     * Member
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false, insertable = false)
    private Member member;

    public static Board createBoard(String title, String content, Member member) {
        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeStatus(int status) {
        this.status = status;
    }
}
