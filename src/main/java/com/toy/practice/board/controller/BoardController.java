package com.toy.practice.board.controller;

import com.toy.practice.board.dto.BoardResponse;
import com.toy.practice.board.model.Board;
import com.toy.practice.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private final BoardService boardService;

    public String list(Model model){
        List<BoardResponse> boards = boardService.getBoards(1,10);
        model.addAttribute("boards", boards);
        return "boards/list";
    }

    public String list(Model model, @RequestParam int page,@RequestParam int size){
        List<BoardResponse> boards = boardService.getBoards(page, size);
        model.addAttribute("boards", boards);
        return "boards/list";
    }


}
