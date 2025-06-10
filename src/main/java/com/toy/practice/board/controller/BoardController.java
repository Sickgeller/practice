package com.toy.practice.board.controller;

import com.toy.practice.board.app.BoardAppService;
import com.toy.practice.board.dto.BoardCreateRequest;
import com.toy.practice.category.dto.BoardCategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private final BoardAppService boardAppService;

    @GetMapping
    public String boardsList(Model model){
        log.info("board list 출력 사이트 이동");
        List<BoardCategoryResponse> boardsCategories = boardAppService.getBoardsCategory();
        return null;
    }
//    @GetMapping
//    public String list(Model model,
//                      @RequestParam(value = "page", defaultValue = "1") int page,
//                      @RequestParam(value = "size", defaultValue = "10") int size) {
//
//        log.info("boards 연결 page : {} size : {}",page, size);
//        List<BoardResponse> boards = boardAppService.getBoards(page, size);
//        model.addAttribute("boards", boards);
//        return "boards/list";
//    }

    @GetMapping("/writeForm")
    public String createForm() {
        return "boards/write";
    }

    @GetMapping("/write")
    public String create(@ModelAttribute BoardCreateRequest request, Model model) {
        boardAppService.create(request);
        return "redirect:/boards";
    }
}
