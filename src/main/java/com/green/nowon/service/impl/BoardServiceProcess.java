package com.green.nowon.service.impl;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.services.s3.AmazonS3Client;
import com.green.nowon.domain.dto.BoardImgSaveDTO;
import com.green.nowon.domain.dto.BoardListDTO;
import com.green.nowon.domain.dto.ImageListDTO;
import com.green.nowon.domain.dto.SeriesListDTO;
import com.green.nowon.domain.entity.BoardEntity;
import com.green.nowon.domain.entity.ImgEntity;
import com.green.nowon.domain.entity.SeriesImgEntity;
import com.green.nowon.domain.repository.BoardEntityRepository;
import com.green.nowon.domain.repository.BoardImageEntityRepository;
import com.green.nowon.domain.repository.SeriesImageEntityRepository;
import com.green.nowon.service.BoardService;
import com.green.nowon.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceProcess implements BoardService {
	
	private final AmazonS3Client s3Client;
	private final BoardEntityRepository brepo;
	private final BoardImageEntityRepository irepo;
	
	private final SeriesImageEntityRepository siRepo;
	
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
	public void ListProcess(long sno, Model model, int page) {
		int size=12;
		Pageable pageable =PageRequest.of(page-1, size, Sort.by(Direction.DESC, "bno"));
		List<BoardListDTO> result=brepo.findBySeriesSno(sno).stream()
				.map(img->new BoardListDTO(img).defImg(irepo.findByBoardBnoAndIsDef(img.getBno(),true)))
				.collect(Collectors.toList());
		SeriesImgEntity thum=siRepo.findBySeriesSno(sno);
		
		Page<BoardListDTO> pd=new PageImpl<>(result, pageable, result.size());
		
		model.addAttribute("board", result);
		model.addAttribute("tot",pd.getTotalPages());
		model.addAttribute("thum",thum);
		
	}

	@Override
	public void boardDetails(long bno, Model model) {
		List<ImageListDTO> result=irepo.findAllByBoardBno(bno).stream()
					.map(img->new ImageListDTO(img)).collect(Collectors.toList());
		BoardEntity rm= brepo.findById(bno).orElseThrow();
		List<ImageListDTO> imgs=irepo.findAllByBoardBnoAndIsDef(bno,false).stream()
				.map(img->new ImageListDTO(img)).collect(Collectors.toList());
		model.addAttribute("detail",result);
		model.addAttribute("rm",rm); //수정,삭제를 위해 board 값 전송
		model.addAttribute("def",result.get(0));
		model.addAttribute("img",imgs);
	}

	@Override
	public void deleteProcess(long bno) {
		List<ImgEntity> img= irepo.findAllByBoardBno(bno);//한 게시글에 저장된 이미지 조회
		
		for(int i=0; i<img.size(); i++) {//저장돼있는 이미지만큼 bucket에서 삭제
			s3Client.deleteObject(BUCKET, img.get(i).getBucketKey());//bucketkey = UPLOAD_PATH+newName
		}
		
		irepo.deleteByBoardBno(bno); //DB에 있는 이미지정보 삭제
		brepo.deleteById(bno); //DB에 있는 게시글 삭제

	}

	@Override
	public void updateProcess(long bno, BoardImgSaveDTO dto) {
		BoardEntity boardEntity=brepo.findById(bno).orElseThrow();
		
		List<ImageListDTO> oldImg=irepo.findAllByBoardBno(bno).stream()
				.map(img->new ImageListDTO(img)).collect(Collectors.toList());
		
		
		
		String[] bucketKeies=dto.getBucketKey();
		String[] orgNames=dto.getOrgName();
		String[] newNames=dto.getNewName();
		
		for(int i=0; i<bucketKeies.length; i++) {
			if(bucketKeies[i]==null || bucketKeies[i].equals(""))continue;
			String newName=newNames[i];
			String orgName=orgNames[i];
			String uploadKey=UPLOAD_PATH+newName;
			String url;
			for(int j=0; j<oldImg.size(); j++) {
				if(bucketKeies[i]==oldImg.get(j).getBucketKey())continue;
				if(j==i && !bucketKeies[i].equals(oldImg.get(j).getBucketKey())) {
					System.out.println("현재 체크하는 bucketkey :"+bucketKeies[i]);
					System.out.println("기존 bucketkey :"+oldImg.get(j).getBucketKey());
					url=FileUploadUtil.s3TempToSrc(s3Client, BUCKET, bucketKeies[i], uploadKey);
					s3Client.deleteObject(BUCKET, oldImg.get(j).getBucketKey());
					irepo.save(
							irepo.save(ImgEntity.builder()
									.ino(oldImg.get(j).getIno())
									.bucketKey(uploadKey)
									.isDef(oldImg.get(j).isDef())
									.isList(oldImg.get(j).isList())
									.url(url).orgName(orgName).newName(newName)
									.board(boardEntity)
									.build()));
					
				}else if(i>oldImg.size()-1 && j!=i) {
					System.out.println("새로운 bucketkey :"+bucketKeies[i]);
					url=FileUploadUtil.s3TempToSrc(s3Client, BUCKET, bucketKeies[i], uploadKey);
					irepo.save(ImgEntity.builder()
							.bucketKey(uploadKey)
							.isDef(false).isList(true)
							.url(url).orgName(orgName).newName(newName)
							.board(boardEntity)
							.build());
					break;
				}
				
			}

		}
		
	}
	
	
	

}
