package com.yashsales.outputbeans;

import com.yashsales.constants.ApplicationConstants;

public class BaseResponse {
	private String responseStatus = ApplicationConstants.ResponseConstants.RESPONSE_FAILURE;
	private String message;
	private String code;

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
