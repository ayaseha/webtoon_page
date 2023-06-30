package com.green.nowon.domain.dto;

import com.green.nowon.domain.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SeriesSaveDTO {
	
	private String title;
	private String synopsis;
	private String writer;
	private Category category;
	
}
