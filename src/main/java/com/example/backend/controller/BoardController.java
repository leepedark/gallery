package com.example.backend.controller;

import com.example.backend.dto.BoardDto;
import com.example.backend.entity.Board;
import com.example.backend.repository.BoardRepository;
import com.example.backend.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;
    private final JwtService jwtService;

    @GetMapping("/api/board/list")
    public ResponseEntity list(@PageableDefault(size = 2) Pageable pageable,
                               @RequestParam(required = false, defaultValue = "") String searchText) {

        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);

        return new ResponseEntity(boards, HttpStatus.OK);
    }

    @GetMapping("/api/board/{id}")
    public ResponseEntity readArticle(@PathVariable int id) {

        Board boards = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        return new ResponseEntity(boards, HttpStatus.OK);
    }

    @PostMapping("/api/board")
    public ResponseEntity postArticle(@CookieValue(value = "token") String token, @Valid @RequestBody BoardDto boardDto, BindingResult bindingResult) {

        if (!jwtService.isValid(token)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        int memberId = jwtService.getId(token);

        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .memberId(memberId)
                .createAt(LocalDateTime.now())
                .build();

        boardRepository.save(board);

        return new ResponseEntity(board, HttpStatus.OK);

    }

    @PatchMapping("/api/board")
    public Board update(@RequestBody BoardDto boardDto) {
        Board entity = boardRepository.findById(boardDto.getId()).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        entity.setTitle(boardDto.getTitle());
        entity.setContent(boardDto.getContent());

        return boardRepository.save(entity);
    }

    @DeleteMapping("/api/board/{id}")
    public void delete(@PathVariable int id) {
        Board entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        boardRepository.delete(entity);
    }


}
