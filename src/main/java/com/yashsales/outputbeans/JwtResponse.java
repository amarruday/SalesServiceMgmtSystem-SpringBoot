package com.yashsales.outputbeans;

public class JwtResponse extends BaseResponse {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JwtResponse(String token) {
		super();
		this.token = token;
	}
	
	public JwtResponse() {
		super();
	}
	
}
