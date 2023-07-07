package com.green.nowon.service;

import org.springframework.ui.Model;

import com.green.nowon.domain.dto.BoardImgSaveDTO;

public interface BoardService {

	void boardSaveProcess(BoardImgSaveDTO dto);

	void ListProcess(long sno, Model model);


}
