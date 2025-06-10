package com.toy.practice.board.service;

import com.toy.practice.board.model.Board;
import com.toy.practice.category.dto.BoardCategoryResponse;
import com.toy.practice.category.model.BoardCategory;

import java.util.List;

public interface BoardService {

    /**
     * 보드를 가져오는 메서드
     *
     * @param page 페이지수
     * @param size 한페이지에 불러올 사이즈
     * @return Board List 객체 반환
     */
    public List<Board> getBoards(int page, int size);


    /**
     * 보드 작성 메서드
     * @param board 작성할 board가 담김
     */
    void create(Board board);

    /**
     * 보드 카테고리를 가져오는 메서드
     * @return BoardCategory List 반환
     */
    List<BoardCategory> getBoardsCategory();
}
