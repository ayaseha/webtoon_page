package com.green.nowon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.nowon.domain.dto.SeriesSaveDTO;
import com.green.nowon.service.SeriesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminController {
	
	@GetMapping("/admin")//관리자 페이지 진입
	public String admin() {
		return "admin/default";
	}
	
	@GetMapping("/admin/series") //작품시리즈 등록페이지
	public String adminSeries() {
		return "admin/series";
	}
	
	@GetMapping("/admin/write") //시리즈별 회차 등록 페이지
	public String adminWrite() {
		return "admin/write";
	}
	
/////////////////////////////////////////////////////////////////////
	
	private final SeriesService seDao;
	
	@PostMapping("/admin/series/new")
	public String seriesSave(SeriesSaveDTO dto) {
		seDao.seriesSaveProcess(dto);
		return "redirect:/admin";
	}
	

}
