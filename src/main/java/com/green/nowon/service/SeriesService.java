package com.green.nowon.service;

import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.green.nowon.domain.dto.SeriesSaveDTO;

public interface SeriesService {

	void seriesSaveProcess(SeriesSaveDTO dto);

	Map<String, String> tempUpload(MultipartFile tempImg);

	void seriesListProcess(String name, Model model, int page);

}
