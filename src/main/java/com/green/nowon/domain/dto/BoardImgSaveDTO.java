package com.green.nowon.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BoardImgSaveDTO {
	
	/////////////////////
	private String[] bucketKey;
	private String[] orgName;
	private String[] newName;
	
}
