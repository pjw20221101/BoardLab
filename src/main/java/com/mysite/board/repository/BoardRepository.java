package com.mysite.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.board.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	
	
	
	
}
