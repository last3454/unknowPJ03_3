package com.unknown.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("###########SecurityFilterChain############");
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.httpBasic().disable() // 기본설정 사용안함 - 기본설정은 비인증시 로그인 화면으로 이동함
				.csrf().disable() // csrf 보안 disabled 처리
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //세션 사용안함
				.authorizeHttpRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers("/api/auth/signin", "/api/auth/token-check").permitAll()
				.antMatchers("/api/**").authenticated()
				.anyRequest().permitAll()
				.and().formLogin().disable().build();
	}
}
