package com.yashsales.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "otp_details")
public class OtpDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long otpId;
	private Long userId;
	private String otp;
	private Timestamp creationTime;
	private Timestamp validTill;
	private String purpose;
	private String status;
}
