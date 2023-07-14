package com.green.nowon.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.s3.AmazonS3Client;
import com.green.nowon.domain.dto.SeriesListDTO;
import com.green.nowon.domain.dto.SeriesSaveDTO;
import com.green.nowon.domain.entity.Category;
import com.green.nowon.domain.entity.SeriesEntity;
import com.green.nowon.domain.entity.SeriesImgEntity;
import com.green.nowon.domain.repository.SeriesEntityRepository;
import com.green.nowon.domain.repository.SeriesImageEntityRepository;
import com.green.nowon.service.SeriesService;
import com.green.nowon.utils.FileUploadUtil;
import com.green.nowon.utils.PageData;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SeriesServiceProcess implements SeriesService {
	
	private final SeriesEntityRepository srepo;
	private final SeriesImageEntityRepository imgRepo;
	private final AmazonS3Client s3Client;
	
	@Value("${cloud.aws.s3.bucket.temp}")
	private String TEMP_PATH;
	@Value("${cloud.aws.s3.bucket.series}")
	private String SERIES_PATH;
	@Value("${cloud.aws.s3.bucket}")
	private String BUCKET;
 
	@Override
	public void seriesSaveProcess(SeriesSaveDTO dto) {
		
		String uploadKey=SERIES_PATH+dto.getNewName();
		String url=FileUploadUtil.s3TempToSrc(s3Client, BUCKET, dto.getBucketKey(), uploadKey);
		SeriesEntity entity=SeriesEntity.builder()
				.title(dto.getTitle())
				.synopsis(dto.getSynopsis())
				.writer(dto.getWriter())
				.category(dto.getCategory())
				.build();
		
		imgRepo.save(SeriesImgEntity.builder()
					.url(url)
					.orgName(dto.getOrgName())
					.newName(dto.getNewName())
					.bucketKey(uploadKey)
					.series(srepo.save(entity))
					.build());

		
	}

	@Override
	public Map<String, String> tempUpload(MultipartFile tempImg) {
		return FileUploadUtil.s3Upload(s3Client, BUCKET, TEMP_PATH , tempImg);
	}

	@Override
	public void seriesListProcess(String name, Model model, int page) {
		int size=12;
		Pageable pageable =PageRequest.of(page-1, size, Sort.by(Direction.DESC, "sno"));
		List<SeriesListDTO> result= srepo.findAllByCategory(Category.valueOf(name.toUpperCase())).stream()
													.map(img->new SeriesListDTO(img)
															.defImg(imgRepo.findBySeries(img)))
													.collect(Collectors.toList());
		
		Page<SeriesListDTO> pd=new PageImpl<>(result, pageable, result.size());
		model.addAttribute("mv",result);
		model.addAttribute("tot",pd.getTotalPages());
		
		
	}

	@Override
	public ModelAndView allListProcess(int page) {
		ModelAndView model=new ModelAndView("board/rest-list");
		List<SeriesListDTO> result=srepo.findAll().stream()
												.map(img->new SeriesListDTO(img)
														.defImg(imgRepo.findBySeries(img)))
												.collect(Collectors.toList());
		
		model.addObject("mv", result);
		//model.addObject("pd",PageData.create(page, 4, (int)srepo.count()));
		return model;
	}

	@Override
	public ModelAndView selectListProcess() {
		ModelAndView mv=new ModelAndView("admin/rest-series");
		mv.addObject("list", srepo.findAll());
		return mv;
	}

	
	
}
