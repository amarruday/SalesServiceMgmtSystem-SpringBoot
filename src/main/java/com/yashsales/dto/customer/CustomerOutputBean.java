package com.yashsales.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOutputBean {
	private Long customerId;
	private String customerName;
	private String customerEmail;
	private String mobileNumber;
	private String address;
	private String customerType;
	private boolean sendSms;
	private boolean sendEmail;
	private String birthDate;
	private String status;
}
