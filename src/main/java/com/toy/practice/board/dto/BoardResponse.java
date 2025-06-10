package com.toy.practice.board.dto;

import com.toy.practice.board.model.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
    private Long boardId;
    private String title;
    private String content;
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BoardResponse of(Board board){
        return BoardResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .id(board.getMember().getId())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdateAt())
                .build();
    }
}
