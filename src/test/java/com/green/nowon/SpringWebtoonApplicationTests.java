package com.green.nowon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.green.nowon.domain.entity.Category;
import com.green.nowon.domain.entity.SeriesEntity;
import com.green.nowon.domain.entity.SeriesImgEntity;
import com.green.nowon.domain.entity.UserEntity;
import com.green.nowon.domain.repository.SeriesEntityRepository;
import com.green.nowon.domain.repository.SeriesImageEntityRepository;
import com.green.nowon.domain.repository.UserEntityRepository;
import com.green.nowon.security.MyRole;
import com.green.nowon.utils.FileUploadUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class SpringWebtoonApplicationTests {
	
	@Autowired
	private UserEntityRepository repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private SeriesEntityRepository srepo;
	@Autowired
	private SeriesImageEntityRepository imgRepo;
	
	
	//@Test
	void contextLoads() {
		
		repo.save(UserEntity.builder()
				.email("admin").password(encoder.encode("1234")).nickName("관리자")
				.build().addRole(MyRole.ADMIN).addRole(MyRole.USER));
		
		log.info("저장완료");
	}
	
	//@Test
	void jpatest() {
		MultipartFile file;
		
//		System.out.println(imgRepo.findBySeries(srepo.findById(3L).orElseThrow())); ;
	}
	
	@Test
	void seriesCreate() {
		
		for (int i=101; i<=150; i++) {
			SeriesEntity entity= SeriesEntity.builder()
			.title("테스트작품 로맨스"+i)
			.synopsis("이것은 로맨스 장르의 테스트 입력입니다. ")
			.writer("테스트작가"+i)
			.category(Category.ROMANCE)
			.build();
			//
			
			imgRepo.save(SeriesImgEntity.builder()
					.url("../images/cat2.jpg")
					.orgName("cat2.jpg")
					.newName("cat-"+i+".jpg")
					.bucketKey("")
					.series(srepo.save(entity))
					.build());
			
		}
		
	}
}
