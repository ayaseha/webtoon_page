package com.green.nowon.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.green.nowon.domain.dto.SeriesSaveDTO;

public interface SeriesService {

	void seriesSaveProcess(SeriesSaveDTO dto);

	Map<String, String> tempUpload(MultipartFile tempImg);

	void seriesListProcess(String name, Model model, int page);

	ModelAndView allListProcess(int page);

	ModelAndView selectListProcess();

	void updateProcess(long sno, SeriesSaveDTO dto);

	void seriesUpdate(long sno, Model model);


}
