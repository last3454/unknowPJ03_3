package com.unknown.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable{

	private static final long serialVersionUID = 1L;

	private String DEFAULT_SECERET = "yhchoi!@#";

	/**
	* 클레임(Claim)을 생성한다.
	* @return Map<String, Object> 클레임(Claim)
	*/
	public String generateToken(String subject, Map<String,Object> claims, long jwtTokenValidity) {
		if(claims == null) {
			claims = new HashMap<String, Object>();
		}

		return Jwts.builder().setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000 ))
				.signWith(SignatureAlgorithm.HS512, DEFAULT_SECERET) //해당 부분 설정시 자동으로 header에 값 설정
				.compact();
	}

    /**
    * Token 에서 Claim 을 가져온다.
    * @param token JWT 토큰
    * @return Claims 클레임
    */
	public Claims getAllClaimsFromToken(String token) {
		return this.getAllClaimsFromToken(token, DEFAULT_SECERET);
	}

    /**
    * Token 에서 Claim 을 가져온다.
    * @param token JWT 토큰
    * @param seceretKey
    * @return Claims 클레임
    */
	public Claims getAllClaimsFromToken(String token, String seceretKey) {
		return Jwts.parser().setSigningKey(seceretKey).parseClaimsJws(token).getBody();
	}

    /**
    * Claim 특정 값 가져온다.
    * @param Claims claims
    * @param claimResolver
    * @return T
    */
	public <T> T getClaimsFromToken(Claims claims, Function<Claims,T> claimResolver) {
		return claimResolver.apply(claims);
	}
}
