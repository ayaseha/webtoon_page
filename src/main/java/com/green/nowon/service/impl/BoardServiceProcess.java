package com.green.nowon.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.amazonaws.services.s3.AmazonS3Client;
import com.green.nowon.domain.dto.BoardImgSaveDTO;
import com.green.nowon.domain.dto.BoardListDTO;
import com.green.nowon.domain.entity.BoardEntity;
import com.green.nowon.domain.entity.ImgEntity;
import com.green.nowon.domain.repository.BoardEntityRepository;
import com.green.nowon.domain.repository.BoardImageEntityRepository;
import com.green.nowon.service.BoardService;
import com.green.nowon.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceProcess implements BoardService {
	
	private final AmazonS3Client s3Client;
	private final BoardEntityRepository brepo;
	private final BoardImageEntityRepository irepo;
	
	@Value("${cloud.aws.s3.bucket.temp}")
	private String TEMP_PATH;
	@Value("${cloud.aws.s3.bucket.upload}")
	private String UPLOAD_PATH;
	@Value("${cloud.aws.s3.bucket}")
	private String BUCKET;
	
	@Override
	public void boardSaveProcess(BoardImgSaveDTO dto) {
		
		BoardEntity entity= brepo.save(BoardEntity.builder()
				.subTitle(dto.getSubTitle())
				.series(dto.getSeries())
				.build());
		
		
		String[] bucketKeies=dto.getBucketKey();
		String[] orgNames=dto.getOrgName();
		String[] newNames=dto.getNewName();
		
		for(int i=0; i<bucketKeies.length; i++) {
			if(bucketKeies[i]==null || bucketKeies[i].equals(""))continue;
			String newName=newNames[i];
			String orgName=orgNames[i];
			String uploadKey=UPLOAD_PATH+newName;
			String url=FileUploadUtil.s3TempToSrc(s3Client, BUCKET, bucketKeies[i], uploadKey);
			if(i==0) {
				irepo.save(ImgEntity.builder()
						.bucketKey(uploadKey)
						.isDef(true).isList(true)
						.url(url).orgName(orgName).newName(newName)
						.board(entity)
						.build());
			}else {
			
				irepo.save(ImgEntity.builder()
						.bucketKey(uploadKey)
						.isDef(false)
						.isList(true)
						.url(url).orgName(orgName).newName(newName)
						.board(entity)
						.build());
			}
			
			
		}
		
	}

	@Override
	public void ListProcess(long sno, Model model) {
		List<BoardListDTO> result=brepo.findBySeriesSno(sno).stream()
				.map(img->new BoardListDTO(img).defImg(irepo.findByBoardBnoAndIsDef(img.getBno(),true)))
				.collect(Collectors.toList());
		model.addAttribute("board", result);
		
	}

	@Override
	public void boardDetails(long bno, Model model) {
		List<ImgEntity> result=irepo.findAllByBoardBno(bno).stream().collect(Collectors.toList());
		
		
		model.addAttribute("detail",result);
		//model.addAttribute("rm",result);
		
	}

	@Override
	public void deleteProcess(long bno) {
		List<ImgEntity> img= irepo.findAllByBoardBno(bno);
		
		for(int i=0; i<img.size(); i++) {
			String bucketKey=img.get(i).getBucketKey();
			s3Client.deleteObject(BUCKET, bucketKey);
		}
		
		irepo.deleteByBoardBno(bno);
		brepo.deleteById(bno);

		
	}


}
