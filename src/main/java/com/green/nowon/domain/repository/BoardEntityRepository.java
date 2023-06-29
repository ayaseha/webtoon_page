package com.green.nowon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.nowon.domain.entity.BoardEntity;

public interface BoardEntityRepository extends JpaRepository<BoardEntity, Long> {

}
