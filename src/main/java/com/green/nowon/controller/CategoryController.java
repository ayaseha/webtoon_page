package com.green.nowon.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.green.nowon.domain.entity.Category;
import com.green.nowon.service.BoardService;
import com.green.nowon.service.SeriesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CategoryController {
	
	private final BoardService bs;
	
	private final SeriesService service;
	
	@ResponseBody // ModelAndView : html을 을 리턴
	@GetMapping("/common/category")
	public ModelAndView category() {
		ModelAndView mv=new ModelAndView("category/list");
		mv.addObject("list", Category.values());
		return mv;
	}
	
	@GetMapping("/web/{name}")
	public String category(@PathVariable String name, Model model, @RequestParam(defaultValue = "1") int page) {
		service.seriesListProcess(name,model,page);
		return "board/list-main";
	}

}
