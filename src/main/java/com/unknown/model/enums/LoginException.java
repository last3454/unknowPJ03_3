package com.unknown.model.enums;

public enum LoginException implements ExceptionCode {

	USER_EMPTY("아이디 또는 비번이 틀렸습니다."),
	USER_PASS_FAIL("아이디 또는 비번이 틀렸습니다."),
	USER_PASS_FAIL_LIMIT("아이디 또는 비번이 틀렸습니다.");

	private String message;

	LoginException(String message){
		this.message = message;
	}

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
