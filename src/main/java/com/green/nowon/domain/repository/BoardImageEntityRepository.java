package com.green.nowon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.nowon.domain.entity.ImgEntity;

public interface BoardImageEntityRepository extends JpaRepository<ImgEntity, Long> {

}
