package com.green.nowon.domain.dto;

import com.green.nowon.domain.entity.SeriesEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BoardImgSaveDTO {
	
	private String subTitle;
	private String content;
	private SeriesEntity series;
	/////////////////////
	private String[] bucketKey;
	private String[] orgName;
	private String[] newName;
	
}
