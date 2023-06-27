package com.green.nowon.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "board")
@Entity
public class BoardEntity extends BaseDateEntity {
	
	private long bno;
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	

}
