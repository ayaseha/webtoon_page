package com.green.nowon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests(authorize -> authorize
				.antMatchers("/images/**","/css/**","/js/**","/webjars/**","/favicon.ico*").permitAll()	
				.antMatchers("/","/signup","/common/**","/web/**","/user/**","/noti/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin(form->form
					.loginPage("/signin")
					.usernameParameter("email") //default: username
					.permitAll()
					//UserDetailsService
			)
			.logout(logout->logout
					.logoutUrl("/logout") //csrf 적용시 Post로 요청해야 로그아웃 가능
					.logoutSuccessUrl("/")//default is "/login?logout
			)
			//.csrf(csrf->csrf.disable()) //jwt 사용한다면
			;
		return http.build();
	}
}
