package com.green.nowon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.green.nowon.domain.entity.Category;
import com.green.nowon.service.SeriesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final SeriesService service;
	
	@ResponseBody // ModelAndView : html을 을 리턴
	@GetMapping("/common/list")
	public ModelAndView mainList() {
		return service.allListProcess();
	}

}
