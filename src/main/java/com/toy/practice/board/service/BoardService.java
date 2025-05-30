package com.toy.practice.board.service;

import com.toy.practice.board.dto.BoardResponse;
import com.toy.practice.board.model.Board;

import java.util.List;

public interface BoardService {

    /**
     * 보드를 가져오는 메서드
     * @param page 페이지수
     * @param size 한페이지에 불러올 사이즈
     * @return Board List 객체 반환
     */
    public List<BoardResponse> getBoards(int page, int size);


}
