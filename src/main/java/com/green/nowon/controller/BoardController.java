package com.green.nowon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	//작품 회차 생성
	@PostMapping("/admin/write/new")
	public String boardSave(BoardImgSaveDTO dto) {
		boDao.boardSaveProcess(dto);
		return "redirect:/admin";
	}
	
	//회차리스트 이동
	@GetMapping("/web/{name}/{sno}")
	public String contentMain(@PathVariable String name, @PathVariable long sno, Model model, @RequestParam(defaultValue = "1") int page) {
		boDao.ListProcess(sno,model,page);
		return "board/list";
	}
	
	//회차 수정페이지 이동
	@GetMapping("/web/{name}/{sno}/update")
	public String seriesUpdatePage(@PathVariable String name, @PathVariable long sno, Model model) {
		service.seriesUpdate(sno,model);
		return "admin/series-update";
	}
	
	//회차 세부페이지 이동
	@GetMapping("/web/{name}/{sno}/{bno}")
	public String details(@PathVariable String name, @PathVariable long sno, @PathVariable long bno, Model model) {
		boDao.boardDetails(bno,model);
		return "board/details";
	}
	
	//회차 삭제
	@DeleteMapping("/web/{name}/{sno}/{bno}")
	public String deleteBoard(@PathVariable String name, @PathVariable long sno, @PathVariable long bno) {
		boDao.deleteProcess(bno);
		return "redirect:/web/{name}/{sno}";
	}
	
	//회차수정 
	//1. 수정페이지로 이동하면서 기존 데이터도 함께 이동
	//2. 수정페이지에서 기존 데이터 표시
	@GetMapping("/web/{name}/{sno}/{bno}/update")
	public String detailsUpdatePage(@PathVariable String name, @PathVariable long sno, @PathVariable long bno, Model model) {
		boDao.boardDetails(bno,model);
		return "admin/update";
	}
	
	//3. 이미지 수정시 s3에서 해당 이미지 삭제 후 수정이미지 등록
	//DB에서는 pk값이 변경되지 않도록 버킷주소, url값만 변경 -> 이미지 순서가 중요하기 때문
	//취소를 눌렀을때는 수정되지 않도록 미리보기는 일시적으로 숨김처리 등으로 처리
	
	@PutMapping("/web/{name}/{sno}/{bno}/update")
	public String detailsUpdate(@PathVariable String name, @PathVariable long sno, @PathVariable long bno, BoardImgSaveDTO dto) {
		boDao.updateProcess(bno,dto);
		return "redirect:/web/{name}/{sno}/{bno}";
	}
	
	
}
