package com.green.nowon.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.green.nowon.domain.entity.BoardEntity;

public interface BoardEntityRepository extends JpaRepository<BoardEntity, Long> {

	List<BoardEntity> findBySeriesSno(long sno);

	Page<BoardEntity> findBySeriesSno(long sno, Pageable pageable);

	int countBySeriesSno(long sno);



}
