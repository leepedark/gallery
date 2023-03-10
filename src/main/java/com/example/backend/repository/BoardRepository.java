package com.example.backend.repository;

import com.example.backend.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
