package com.green.nowon.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.green.nowon.domain.dto.BoardImgSaveDTO;
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
		
		BoardEntity entity= BoardEntity.builder()
				.subTitle(dto.getSubTitle())
				.series(dto.getSeries())
				.build();
		
		String[] bucketKeies=dto.getBucketKey();
		String[] orgNames=dto.getOrgName();
		String[] newNames=dto.getNewName();
		
		for(int i=0; i<bucketKeies.length; i++) {
			if(bucketKeies[i]==null || bucketKeies[i]=="")continue;
			String newName=newNames[i];
			String orgName=orgNames[i];
			String uploadKey=UPLOAD_PATH+newName;
			String url=FileUploadUtil.s3TempToSrc(s3Client, BUCKET, bucketKeies[i], uploadKey);
			if(i==0) {

				irepo.save(ImgEntity.builder()
						.bucketKey(bucketKeies[i])
						.isDef(true).isList(true)
						.url(url).orgName(orgName).newName(newName)
						.board(entity)
						.build());
			}else {
			
				irepo.save(ImgEntity.builder()
						.bucketKey(bucketKeies[i])
						.isList(true)
						.url(url).orgName(orgName).newName(newName)
						.board(entity)
						.build());
			}
			
			
		}
		
	}


}
