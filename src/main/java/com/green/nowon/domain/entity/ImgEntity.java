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

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image")
@Entity
public class ImgEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ino;
	@Column(nullable = false)
	private String url; //s3경로
	@Column(nullable = false)
	private String orgName; //s3경로
	@Column(nullable = false)
	private String newName; //s3경로
	private String bucketKey; //파일명
	private boolean isList ; 
	private boolean isDef ; 
	
	@JoinColumn(name = "bno")
	@ManyToOne
	private BoardEntity board;
}
