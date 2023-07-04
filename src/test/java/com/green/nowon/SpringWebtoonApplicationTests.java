package com.green.nowon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.green.nowon.domain.entity.SeriesEntity;
import com.green.nowon.domain.entity.SeriesImgEntity;
import com.green.nowon.domain.entity.UserEntity;
import com.green.nowon.domain.repository.SeriesEntityRepository;
import com.green.nowon.domain.repository.SeriesImageEntityRepository;
import com.green.nowon.domain.repository.UserEntityRepository;
import com.green.nowon.security.MyRole;

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
		
		System.out.println(imgRepo.findBySeries(srepo.findById(3L).orElseThrow())); ;
		
	}

}
