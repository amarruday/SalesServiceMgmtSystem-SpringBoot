package com.yashsales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "enquiry_sources")
public class EnquirySource {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long enquirySourceId;
	private String enquirySource;

	public Long getEnquirySourceId() {
		return enquirySourceId;
	}

	public void setEnquirySourceId(Long enquirySourceId) {
		this.enquirySourceId = enquirySourceId;
	}

	public String getEnquirySource() {
		return enquirySource;
	}

	public void setEnquirySource(String enquirySource) {
		this.enquirySource = enquirySource;
	}

	@Override
	public String toString() {
		return enquirySource;
	}
	
	

}
