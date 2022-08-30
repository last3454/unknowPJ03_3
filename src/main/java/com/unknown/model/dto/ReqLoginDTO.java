package com.unknown.model.dto;

import lombok.Data;

@Data
public class ReqLoginDTO {

	private String loginId;

	private String loginPw;

	private String rememberYn;
}