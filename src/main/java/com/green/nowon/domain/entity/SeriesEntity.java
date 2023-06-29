package com.green.nowon.domain.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "series")
@Entity
public class SeriesEntity extends BaseDateEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long sno;
	
	@Column(nullable = false)
	private String title;
	
	private String seriesImg;
	
	@Column(nullable = false)
	private String synopsis;
	
	@Column(nullable = false)
	private String writer;
	
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "category")
	private Category category;
	
	@Column(columnDefinition = "int default 0")
	private int favorite;
	
	@JoinColumn(name = "no")
	@ManyToOne
	private UserEntity user;
}
