package com.unknown.config;

import com.unknown.model.enums.ExceptionCode;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String code;

	private final String message;

	private final Object data;

	public CustomException(ExceptionCode excep) {
		super(excep.getMessage());
		this.message = excep.getMessage();
		this.code = excep.getCode();
		this.data = null;
	}

	public CustomException(String code, String message, String data) {
		super(message);
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}
}
