package com.yashsales.dto.commons;

public class VerifyOtpAndPasswordBean {
	public String otp;
	public long otpId;
	public String newPasssword;
	public String confirmPassword;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public long getOtpId() {
		return otpId;
	}

	public void setOtpId(long otpId) {
		this.otpId = otpId;
	}

	public String getNewPasssword() {
		return newPasssword;
	}

	public void setNewPasssword(String newPasssword) {
		this.newPasssword = newPasssword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
