package com.green.nowon.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.nowon.domain.entity.ImgEntity;

public interface BoardImageEntityRepository extends JpaRepository<ImgEntity, Long> {

	ImgEntity findByBoardBnoAndIsDef(long bno,boolean def);

	List<ImgEntity> findAllByBoardBno(long bno);

	@Transactional
	void deleteByBoardBno(long bno);

	List<ImgEntity> findAllByBoardBnoAndIsDef(long bno, boolean b);


}
