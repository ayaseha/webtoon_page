package com.green.nowon.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.green.nowon.domain.entity.UserEntity;

import lombok.Getter;

@Getter
public class MyUserDetails extends User{

	private static final long serialVersionUID = 1L;
	
	private String nick; 
	private String email;
	
	 MyUserDetails(UserEntity entity) {
		this(entity.getEmail(), entity.getPassword(), entity.getRoleSet().stream()
				.map(role->new SimpleGrantedAuthority(role.roleName()))
				.collect(Collectors.toSet()) );
		this.nick=entity.getNickName();
		this.email=entity.getEmail();
	}

	private MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

}
