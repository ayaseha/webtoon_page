package com.green.nowon.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "board")
@Entity
public class BoardEntity extends BaseDateEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long bno;
	
	@Column(nullable = false)
	private String subTitle;
	
	@Column(columnDefinition = "int default 0")
	private int readCount;
	
	@JoinColumn(name = "sno")
	@ManyToOne
	private SeriesEntity series;
	
//	@JoinColumn(name = "bno")
//	@OneToMany
//	private List<ImgEntity> image;
}
