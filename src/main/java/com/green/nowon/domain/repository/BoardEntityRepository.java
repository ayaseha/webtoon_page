package com.green.nowon.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.nowon.domain.dto.BoardListDTO;
import com.green.nowon.domain.entity.BoardEntity;

public interface BoardEntityRepository extends JpaRepository<BoardEntity, Long> {

	List<BoardEntity> findBySeriesSno(long sno);



}
