package com.toy.practice.board.service;

import com.toy.practice.board.dao.BoardDAO;
import com.toy.practice.board.model.Board;
import com.toy.practice.board.repository.BoardRepository;
import com.toy.practice.category.model.BoardCategory;
import com.toy.practice.category.repository.BoardCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final BoardDAO boardDAO;

    @Override
    public List<Board> getBoards(int page, int size) {
        boardDAO.getBoards(page, size);
        return null;
//        return boardRepository.findAll().stream().collect(BoardResponse::from);
    }

    @Override
    public void create(Board board) {
        boardRepository.save(board);
    }

    @Override
    public List<BoardCategory> getBoardsCategory() {
        return boardCategoryRepository.findAll().stream().toList();
    }
}
