package com.unknown.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.unknown.utils.JwtTokenUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;

	private final RequestMatcher apiRequests = new AntPathRequestMatcher("/api/**");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtHeaderKey = request.getHeader("jwtHeaderKey");
		if(log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("\n").append("URI : ").append(request.getRequestURI());
			sb.append("\n").append("Method : ").append(request.getMethod());
			sb.append("\n").append("jwtHeaderKey : ").append(jwtHeaderKey);
			log.debug(sb.toString());
		}

		if(!apiRequests.matches(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		if(jwtHeaderKey != null) {
			try {
				Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtHeaderKey);
				Date expirDt = jwtTokenUtil.getClaimsFromToken(claims, Claims::getExpiration);
				//토큰이 살아 있는데 context에 존재하지 않을 시 셋팅 해준다.
				if(SecurityContextHolder.getContext().getAuthentication() == null && expirDt.after(new Date())) {

				}

			} catch (IllegalArgumentException e) {
				log.error("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				log.error("JWT Token has Expired");
			}
		}

		filterChain.doFilter(request, response);
	}
}
