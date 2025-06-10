package com.toy.practice.board.app;

import com.toy.practice.board.dto.BoardCreateRequest;
import com.toy.practice.board.dto.BoardResponse;
import com.toy.practice.board.mapper.BoardMapper;
import com.toy.practice.board.model.Board;
import com.toy.practice.board.service.BoardService;
import com.toy.practice.category.dto.BoardCategoryResponse;
import com.toy.practice.category.mapper.BoardCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardAppServiceImpl implements BoardAppService {

    private final BoardMapper boardMapper;
    private final BoardService boardService;
    private final BoardCategoryMapper boardCategoryMapper;


    @Override
    public void create(BoardCreateRequest request) {
        Board board = boardMapper.toEntityFromCreate(request);
        boardService.create(board);
    }

    @Override
    public List<BoardResponse> getBoards(int page, int limit) {
        List<Board> boards = boardService.getBoards(page, limit);
        return List.of();
    }

    @Override
    public List<BoardCategoryResponse> getBoardsCategory() {
        return boardCategoryMapper.toDTOList(boardService.getBoardsCategory());
    }
}
