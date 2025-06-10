package com.toy.practice.board.app;

import com.toy.practice.board.dto.BoardCreateRequest;
import com.toy.practice.board.dto.BoardResponse;
import com.toy.practice.category.dto.BoardCategoryResponse;

import java.util.List;

public interface BoardAppService {

    /**
     * 글 작성 보드
     * @param request 작성할 내용이 담긴 request
     */
    public void create(BoardCreateRequest request);

    List<BoardResponse> getBoards(int page, int limit);

    List<BoardCategoryResponse> getBoardsCategory();
}
