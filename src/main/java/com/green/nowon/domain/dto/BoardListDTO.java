package com.green.nowon.domain.dto;

import com.green.nowon.domain.entity.BoardEntity;
import com.green.nowon.domain.entity.ImgEntity;
import com.green.nowon.domain.entity.SeriesEntity;

import lombok.Getter;

@Getter
public class BoardListDTO {
	
	private long bno;
	private String subTitle;
	private SeriesEntity series;
	
	private String url;
	private String bucketKey;
	private String orgName;
	private String newName;
	
	public BoardListDTO defImg(ImgEntity sie) {
		url=sie.getUrl();
		bucketKey=sie.getBucketKey();
		orgName=sie.getOrgName();
		newName=sie.getNewName();
		return this;
	}
	
	public BoardListDTO(BoardEntity b) {
		bno=b.getBno();
		subTitle=b.getSubTitle();
		series=b.getSeries();
	}


}
