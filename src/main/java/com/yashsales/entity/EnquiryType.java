package com.yashsales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "enquiry_types")
public class EnquiryType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long enquiryTypeId;
	private String enquiryType;
	private String displayColor;
	private String displayColorName;

	public Long getEnquiryTypeId() {
		return enquiryTypeId;
	}

	public void setEnquiryTypeId(Long enquiryTypeId) {
		this.enquiryTypeId = enquiryTypeId;
	}

	public String getEnquiryType() {
		return enquiryType;
	}

	public void setEnquiryType(String enquiryType) {
		this.enquiryType = enquiryType;
	}

	public String getDisplayColor() {
		return displayColor;
	}

	public void setDisplayColor(String displayColor) {
		this.displayColor = displayColor;
	}

	public String getDisplayColorName() {
		return displayColorName;
	}

	public void setDisplayColorName(String displayColorName) {
		this.displayColorName = displayColorName;
	}

}
