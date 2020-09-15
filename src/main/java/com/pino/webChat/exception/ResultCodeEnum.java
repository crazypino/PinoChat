package com.pino.webChat.exception;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
	SUCCESS(true,200, "Sucessful Query"),
	ERROR(true,201, "Null"),
	UNKNOWN_ERROR(false,202, "Unknown Error"),
	PARAM_ERROR(false,203, "Parameter Error"),
	NULL_POINT(false,204, "Null Pointer Error"),
	HTTP_CLIENT_ERROR(false,205,"Array Index Out of Bound");
	
	// if request successfully
	private Boolean success; 
	
	// status code
	private Integer code;
	
	// message
	private String message;
	
	ResultCodeEnum(boolean success, Integer code, String message){
		this.success = success;
		this.code = code;
		this.message = message;
	}
}
