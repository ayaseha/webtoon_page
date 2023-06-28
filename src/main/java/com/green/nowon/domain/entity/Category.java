package com.green.nowon.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
	ROMANCE("로맨스"),
	FANTASY("판타지"),
	MATERIAL_ARTS("무협"),
	LIFE("일상"),
	NOTICE("공지사항");
	
	private final String koName;
	public String koName() {return koName;}
}
