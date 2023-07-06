package com.green.nowon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.green.nowon.domain.dto.BoardImgSaveDTO;
import com.green.nowon.domain.dto.SeriesSaveDTO;
import com.green.nowon.domain.entity.Category;
import com.green.nowon.service.BoardService;
import com.green.nowon.service.SeriesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final SeriesService service;
	private final BoardService boDao;
	
	@ResponseBody // ModelAndView : html을 을 리턴
	@GetMapping("/common/list")
	public ModelAndView mainList(@RequestParam(defaultValue = "1") int page) {
		return service.allListProcess(page);
	}
	
	@PostMapping("/admin/write/new")
	public String boardSave(BoardImgSaveDTO dto) {
		boDao.boardSaveProcess(dto);
		return "redirect:/admin";
	}

}
