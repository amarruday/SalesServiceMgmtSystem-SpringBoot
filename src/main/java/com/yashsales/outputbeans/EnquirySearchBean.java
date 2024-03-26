package com.yashsales.outputbeans;

import java.util.Date;

public class EnquirySearchBean {
	private int pageNumber;
	private int pageSize;
	private Date startDate;
	private Date endDate;
	private Long enquiryTypeId;
	private Long enquirySourceId;
	private String status;
	private Long customerId;
	private Long addedBy;
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getEnquiryTypeId() {
		return enquiryTypeId;
	}
	public void setEnquiryTypeId(Long enquiryTypeId) {
		this.enquiryTypeId = enquiryTypeId;
	}
	public Long getEnquirySourceId() {
		return enquirySourceId;
	}
	public void setEnquirySourceId(Long enquirySourceId) {
		this.enquirySourceId = enquirySourceId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(Long addedBy) {
		this.addedBy = addedBy;
	}
	
}
