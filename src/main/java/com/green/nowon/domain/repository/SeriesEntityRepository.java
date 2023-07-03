package com.green.nowon.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.nowon.domain.entity.Category;
import com.green.nowon.domain.entity.SeriesEntity;

public interface SeriesEntityRepository extends JpaRepository<SeriesEntity, Long> {

	List<SeriesEntity> findAllByCategory(Category category);


}
