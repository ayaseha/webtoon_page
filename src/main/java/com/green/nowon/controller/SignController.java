package com.green.nowon.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.nowon.domain.dto.UserSaveDTO;
import com.green.nowon.domain.repository.UserEntityRepository;
import com.green.nowon.service.SignService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SignController {
	
	private final SignService service;
	private final UserEntityRepository repo;
	
	@PostMapping("/signup")
	public String userSave(@Valid UserSaveDTO dto) {
		service.userSave(dto);
		return "redirect:/signin";
	}
	
	@ResponseBody
	@PostMapping("/user/email-check")
	public boolean emailCheck(String email) {
		if(repo.findByEmail(email).isEmpty()) {
			return true;
		}
		return false;
	}

}
