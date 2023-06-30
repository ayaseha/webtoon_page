package com.green.nowon.service.impl;

import org.springframework.stereotype.Service;

import com.green.nowon.domain.dto.SeriesSaveDTO;
import com.green.nowon.domain.entity.SeriesEntity;
import com.green.nowon.domain.repository.SeriesEntityRepository;
import com.green.nowon.service.SeriesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SeriesServiceProcess implements SeriesService {
	
	private final SeriesEntityRepository srepo;

	@Override
	public void seriesSaveProcess(SeriesSaveDTO dto) {
		srepo.save(SeriesEntity.builder()
				.title(dto.getTitle())
				.synopsis(dto.getSynopsis())
				.writer(dto.getWriter())
				.category(dto.getCategory())
				.build());
		
	}
	
	
}
