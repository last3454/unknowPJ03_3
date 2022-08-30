package com.unknown.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.unknown.config.CustomException;
import com.unknown.data.entity.user.UserEntity;
import com.unknown.data.repo.user.UserRepo;
import com.unknown.model.enums.LoginException;
import com.unknown.model.vo.SessionVO;
import com.unknown.utils.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	final private UserRepo userRepo;

	final private JwtTokenUtil jwtTokenUtil;

	@Transactional
	public SessionVO checkLogin(String loginId, String loginPw, HttpServletRequest request) {
		UserEntity userEntity = userRepo.findByLoginIdIgnoreCase(loginId);

		if(userEntity == null) {
			throw new CustomException(LoginException.USER_EMPTY);
		}

		//비밀번호 5회 이상 불일치
		if(userEntity.getPassFailCnt() >= 5) {
			throw new CustomException(LoginException.USER_PASS_FAIL_LIMIT);
		}

		//비밀번호 불일치
		if(!loginPw.equals(userEntity.getPass())) {
			userEntity.setPassFailCnt(userEntity.getPassFailCnt()+1);
			throw new CustomException(LoginException.USER_PASS_FAIL);
		}

		userEntity.setPassFailCnt(0);
		userRepo.save(userEntity);

		return SessionVO.builder()
				.loginSeq(userEntity.getSeq())
				.loginId(loginId)
				.loginNm("")
				.email("").build();
	}

	public String convertLoginToken(SessionVO sessionVO) {
		//[S]JWT-TOKEN 값 셋팅
		Map<String, Object> claims = new HashMap<String,Object>();
		claims.put("loginId", sessionVO.getLoginId());
		claims.put("loginNm", sessionVO.getLoginNm());

		String subject = sessionVO.getLoginSeq()+"_"+sessionVO.getLoginId();
		String token = jwtTokenUtil.generateToken(subject, claims, 1L * 60L * 60L);

		return token;
		//[E]JWT-TOKEN 값 셋팅
	}
}
