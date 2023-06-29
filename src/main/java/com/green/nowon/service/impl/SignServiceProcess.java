package com.green.nowon.service.impl;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.green.nowon.domain.dto.UserSaveDTO;
import com.green.nowon.domain.entity.UserEntity;
import com.green.nowon.domain.repository.UserEntityRepository;
import com.green.nowon.security.MyRole;
import com.green.nowon.service.SignService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SignServiceProcess implements SignService{
	
	private final UserEntityRepository repo;
	private final PasswordEncoder encoder;
	
	@Override
	public void userSave(@Valid UserSaveDTO dto) {
		repo.save(UserEntity.builder()
					.email(dto.getEmail())
					.password(encoder.encode(dto.getPassword()))
					.nickName(dto.getNickName())
					.build().addRole(MyRole.USER));
		
	}



}
