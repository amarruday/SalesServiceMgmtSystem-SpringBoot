package com.yashsales.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "enquiry_activities")
public class EnquiryActivity  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long enquiryActivityId;
	
	@OneToOne
	@JoinColumn(name = "enquiryId")
	private Enquiry enquiry;
	
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;
	
	private String status;
	private Timestamp recordDate;
	private String remark;

	public Long getEnquiryActivityId() {
		return enquiryActivityId;
	}

	public void setEnquiryActivityId(Long enquiryActivityId) {
		this.enquiryActivityId = enquiryActivityId;
	}

	public Enquiry getEnquiry() {
		return enquiry;
	}

	public void setEnquiry(Enquiry enquiry) {
		this.enquiry = enquiry;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}