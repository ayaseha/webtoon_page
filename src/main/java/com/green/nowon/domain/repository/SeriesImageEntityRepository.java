package com.green.nowon.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.nowon.domain.entity.SeriesEntity;
import com.green.nowon.domain.entity.SeriesImgEntity;

public interface SeriesImageEntityRepository extends JpaRepository<SeriesImgEntity, Long>{


	SeriesImgEntity findBySeries(SeriesEntity sno);

	SeriesImgEntity findBySeriesSno(long sno);


}
