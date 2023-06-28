package com.green.nowon.domain.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@Column(columnDefinition = "int default 0")
	private int readCount;
	
	@Column(nullable = false)
	private String writer;
	
	
	@ManyToOne
	@JoinColumn(name = "user_board_no")
	private UserEntity user;
	
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "category")
	private Category category;

}
