package com.green.nowon.domain.dto;

import com.green.nowon.domain.entity.Category;
import com.green.nowon.domain.entity.SeriesEntity;
import com.green.nowon.domain.entity.SeriesImgEntity;

import lombok.Getter;

@Getter
public class SeriesListDTO {
	
	private long sno;
	private String title;
	private String writer;
	private Category category;
	private int favorite;
			
	/////////////////////
	//표지이미지 정보
	private String url;
	private String bucketKey;
	private String orgName;
	private String newName;
	
	public SeriesListDTO defImg(SeriesImgEntity sie) {
		url=sie.getUrl();
		bucketKey=sie.getBucketKey();
		orgName=sie.getOrgName();
		newName=sie.getNewName();
		return this;
	}
	
	public SeriesListDTO(SeriesEntity entity) {
		sno=entity.getSno();
		title=entity.getTitle();
		category=entity.getCategory();
		writer=entity.getWriter();
		favorite=entity.getFavorite();
	}

}
