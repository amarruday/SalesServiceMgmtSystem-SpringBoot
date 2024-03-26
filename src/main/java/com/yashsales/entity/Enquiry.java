package com.yashsales.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "enquiries")
public class Enquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long enquiryId;

	@OneToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "enquirySourceId")
	private EnquirySource enquirySource;

	@OneToOne
	@JoinColumn(name = "enquiryTypeId")
	private EnquiryType enquiryType;

	@OneToOne
	@JoinColumn(name = "productId")
	private Product product;

	private Long quantity;
	private String productRemark;

	@OneToOne
	@JoinColumn(name = "ADDED_BY", referencedColumnName = "userId")
	private User addedBy;

	private Timestamp addedDate;
	private Timestamp recentActivityDate;
	private String remark;
	private String status;

	@OneToOne
	@JoinColumn(name = "ASSIGNED_TO", referencedColumnName = "userId")
	private User assignedTo;

	@OneToOne
	@JoinColumn(name = "ASSIGNED_BY", referencedColumnName = "userId")
	private User assignedBy;

	private Long createdBy;
	@CreatedDate
	private Timestamp createdDate;
	private Long updatedBy;
	@LastModifiedDate
	private Timestamp updatedDate;

	public Long getEnquiryId() {
		return enquiryId;
	}

	public void setEnquiryId(Long enquiryId) {
		this.enquiryId = enquiryId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public EnquirySource getEnquirySource() {
		return enquirySource;
	}

	public void setEnquirySource(EnquirySource enquirySource) {
		this.enquirySource = enquirySource;
	}

	public EnquiryType getEnquiryType() {
		return enquiryType;
	}

	public void setEnquiryType(EnquiryType enquiryType) {
		this.enquiryType = enquiryType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getProductRemark() {
		return productRemark;
	}

	public void setProductRemark(String productRemark) {
		this.productRemark = productRemark;
	}

	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public Timestamp getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Timestamp addedDate) {
		this.addedDate = addedDate;
	}

	public Timestamp getRecentActivityDate() {
		return recentActivityDate;
	}

	public void setRecentActivityDate(Timestamp recentActivityDate) {
		this.recentActivityDate = recentActivityDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public User getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(User assignedBy) {
		this.assignedBy = assignedBy;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "Enquiry [enquiryId=" + enquiryId + ", customer=" + customer + ", enquirySource=" + enquirySource
				+ ", enquiryType=" + enquiryType + ", product=" + product + ", quantity=" + quantity
				+ ", productRemark=" + productRemark + ", addedBy=" + addedBy + ", addedDate=" + addedDate
				+ ", recentActivityDate=" + recentActivityDate + ", remark=" + remark + ", status=" + status
				+ ", assignedTo=" + assignedTo + ", assignedBy=" + assignedBy + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + "]";
	}
	
	
}
