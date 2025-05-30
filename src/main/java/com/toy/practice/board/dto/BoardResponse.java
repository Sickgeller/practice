package com.toy.practice.board.dto;

import com.toy.practice.board.model.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponse {
    private Long boardId;
    private String title;
    private String content;
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BoardResponse from(Board board){
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
