package com.green.nowon.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.nowon.domain.dto.BoardListDTO;
import com.green.nowon.domain.entity.BoardEntity;
import com.green.nowon.domain.entity.ImgEntity;

public interface BoardImageEntityRepository extends JpaRepository<ImgEntity, Long> {

	ImgEntity findByBoardBnoAndIsDef(long bno,boolean def);

	List<ImgEntity> findAllByBoardBno(long bno);


	void deleteByBoardBno(long bno);


}
