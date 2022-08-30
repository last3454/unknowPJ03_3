package com.unknown.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unknown.config.CustomException;
import com.unknown.model.dto.ReqLoginDTO;
import com.unknown.model.dto.ResLoginDTO;
import com.unknown.model.vo.ResponseVO;
import com.unknown.model.vo.SessionVO;
import com.unknown.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthController {

	final private UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody @Valid ReqLoginDTO params, HttpServletRequest request, Errors errors){
		if(log.isDebugEnabled()) {
			log.debug("ApiAuthController.signin : params = {}", params);
		}

		ResponseVO responseVO = new ResponseVO();
		SessionVO sessionVO	  = null;
		try {
			sessionVO = userService.checkLogin(params.getLoginId(), params.getLoginPw(), request);
		} catch (CustomException e) {
			responseVO.setFail(e.getCode(), e.getMessage(), null);
			return ResponseEntity.ok(responseVO);
		}

		//회원 정보가 존재 시
		if(sessionVO != null) {
			String token = userService.convertLoginToken(sessionVO);
			ResLoginDTO resLoginDTO = ResLoginDTO.builder()
				.loginId(sessionVO.getLoginId())
				.loginNm(sessionVO.getLoginNm())
				.token(token)
				.build();
			responseVO.setOk(resLoginDTO);
		}
		return ResponseEntity.ok(responseVO);
	}
}
