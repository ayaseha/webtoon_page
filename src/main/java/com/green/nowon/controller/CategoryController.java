package com.green.nowon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.green.nowon.domain.entity.Category;

@Controller
public class CategoryController {
	
	@ResponseBody // ModelAndView : html을 을 리턴
	@GetMapping("/common/category")
	public ModelAndView category() {
		ModelAndView mv=new ModelAndView("category/list");
		mv.addObject("list", Category.values());
		return mv;
	}
	
	@GetMapping("/web/{name}")
	public String category(@PathVariable String name ) {
		
		return "board/list";
	}

}
