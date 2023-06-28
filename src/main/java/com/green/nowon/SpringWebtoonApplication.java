package com.green.nowon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringWebtoonApplication {
	
	static { 
	    System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true"); 
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringWebtoonApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(13);
	}
}
