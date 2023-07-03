package com.green.nowon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.nowon.domain.entity.SeriesImgEntity;

public interface SeriesImageEntityRepository extends JpaRepository<SeriesImgEntity, Long>{

	SeriesImgEntity findAllBySeries(long sno);


}
