package com.boot.board230801.controller;

import com.boot.board230801.model.Board;
import com.boot.board230801.repository.BoardRepository;
import com.boot.board230801.validator.BoardValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardValidator boardValidator;

    @GetMapping("/list")
//    public String list(Model model){
//    public String list(Model model, Pageable pageable){
//    Pageable : default 0, 20 (PageableDefault 에서는 디폴트가 10)
//        public String list(Model model, @PageableDefault(size = 2) Pageable pageable){
//    searchText : 검색조건 추가
//        public String list(Model model, @PageableDefault(size = 2) Pageable pageable, String searchText){
//    검색시 조회되게 추가(null 처리)
        public String list(Model model, @PageableDefault(size = 2) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText){
        log.info("@# searchText =>"+searchText);

//        List<Board> boards = boardRepository.findAll();
//        Page<Board> boards = boardRepository.findAll(PageRequest.of(1, 20));
//        page 는 0부터 시작
//        Page<Board> boards = boardRepository.findAll(PageRequest.of(0, 20));
//            Page<Board> boards = boardRepository.findAll(pageable);
//        검색조건 추가
//            Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
            Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);

//        getPageNumber() : 해당 페이지 번호
//        Math.max : 여러개중 최대값
//        Math.min : 여러개중 최소값
//        getTotalPages() : 전체 페이지 갯수
//        5개 표시되도록 설정 : getPageNumber(0~4 페이지)
//        0-4=0, 1-4=0, 2-4=0, 3-4=0, 4-4=0
//        int startPage = Math.max(0, boards.getPageable().getPageNumber() - 4);
//        화면에 1페이지부터 표시
            int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
//        0+4=4, 1+4=4, 2+4=4, 3+4=4, 4+4=4
            int endPage = Math.min( boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("boards", boards);

            return "board/list";
        }
        @GetMapping("/form")
//    public String form(Model model){
        public String form(Model model, @RequestParam(required = false) Long id){
            log.info("@#id=>"+id);

            if (id == null){
                model.addAttribute("board", new Board());
            }else {
//            Optional<Board> board = boardRepository.findById(id);
                Board board = boardRepository.findById(id).orElse(null);
                model.addAttribute("board", board);
            }
            return "board/form";
        }
        @PostMapping("/form")
//    public String form(@ModelAttribute Board board, Model model){
        public String form(@Valid Board board, BindingResult bindingResult){
            boardValidator.validate(board, bindingResult);

            if (bindingResult.hasErrors()) {
                return "board/form";
            }

            boardRepository.save(board);
            return "redirect:/board/list";
        }
    }
