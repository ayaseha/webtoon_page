package com.green.nowon.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

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
		
		List<SeriesListDTO> result= srepo.findAllByCategory(Category.valueOf(name.toUpperCase())).stream()
													.map(img->new SeriesListDTO(img)
															.defImg(imgRepo.findAllBySeries(img.getSno())))
													.collect(Collectors.toList());
		model.addAttribute("list",result);
		
	}
	
	
}
