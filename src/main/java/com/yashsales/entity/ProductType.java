package com.yashsales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product_types")
public class ProductType  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productTypeId;

	@OneToOne
	@JoinColumn(name = "productCatagoryId")
	private ProductCatagory productCatagory;
	private String productName;
	private String ticketPrefix;
	private boolean status;
	@JsonIgnore
	private String notes;

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public ProductCatagory getProductCatagory() {
		return productCatagory;
	}

	public void setProductCatagory(ProductCatagory productCatagory) {
		this.productCatagory = productCatagory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTicketPrefix() {
		return ticketPrefix;
	}

	public void setTicketPrefix(String ticketPrefix) {
		this.ticketPrefix = ticketPrefix;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "ProductType [productTypeId=" + productTypeId + ", productCatagory=" + productCatagory.getProductCatagoryName() + ", productName="
				+ productName + ", ticketPrefix=" + ticketPrefix + ", status=" + status + ", notes=" + notes + "]";
	}
	
	

}
