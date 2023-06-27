package com.green.nowon.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.green.nowon.security.MyRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
public class UserEntity extends BaseDateEntity{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long no;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	private String password;
	
	@Column(nullable = false, unique = true)
	private String nickName;
	
	
	@Builder.Default 
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "role")
	@ElementCollection(fetch = FetchType.EAGER)//1:N
	private Set<MyRole> roleSet=new HashSet<>();
	
	public UserEntity addRole(MyRole role) {
		roleSet.add(role);
		return this;
	}

}
