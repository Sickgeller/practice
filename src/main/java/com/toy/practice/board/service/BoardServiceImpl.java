package com.toy.practice.board.service;

import com.toy.practice.board.dto.BoardResponse;
import com.toy.practice.board.model.Board;
import com.toy.practice.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<BoardResponse> getBoards(int page, int size) {
        return null;
//        return boardRepository.findAll().stream().collect(BoardResponse::from);
    }
}
