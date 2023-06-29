package com.green.nowon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.green.nowon.domain.entity.UserEntity;
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
	
	//@Test
	void contextLoads() {
		
		repo.save(UserEntity.builder()
				.email("admin").password(encoder.encode("1234")).nickName("관리자")
				.build().addRole(MyRole.ADMIN).addRole(MyRole.USER));
		
		log.info("저장완료");
	}

}
