package com.green.nowon.domain.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.green.nowon.domain.dto.SeriesListDTO;
import com.green.nowon.domain.entity.Category;
import com.green.nowon.domain.entity.SeriesEntity;

public interface SeriesEntityRepository extends JpaRepository<SeriesEntity, Long> {

	List<SeriesEntity> findAllByCategory(Category category);

	List<SeriesEntity> findByCategory(Category valueOf);

	int countByCategory(Category valueOf);

	Page<SeriesEntity> findAllByCategory(Category valueOf, Pageable pageable);




}
