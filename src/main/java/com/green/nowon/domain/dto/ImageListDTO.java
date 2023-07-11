package com.green.nowon.domain.dto;

import com.green.nowon.domain.entity.BoardEntity;
import com.green.nowon.domain.entity.ImgEntity;
import com.green.nowon.domain.entity.SeriesEntity;

import lombok.Getter;

@Getter
public class ImageListDTO {
	
	private long ino;
	private String url;
	private String bucketKey;
	private String orgName;
	private String newName;
	private boolean isList; 
	private boolean isDef; 
	private BoardEntity board;
	

	public ImageListDTO(ImgEntity i) {
		ino=i.getIno();
		url=i.getUrl();
		bucketKey=i.getBucketKey();
		orgName=i.getOrgName();
		newName=i.getNewName();
		isDef=i.isDef();
		isList=i.isList();
		board=i.getBoard();
	}


}
