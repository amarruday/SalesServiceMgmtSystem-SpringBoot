package com.yashsales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;

	@OneToOne
	@JoinColumn(name = "productTypeId")
	private ProductType productType;

	@OneToOne
	@JoinColumn(name = "brandId")
	private Brand brand;

	private String productName;
	private Long warrantyInYears;
	private String ticketPrefix;
	private boolean status;
	private String additionalInfoOne;
	private String additionalInfoTwo;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getWarrantyInYears() {
		return warrantyInYears;
	}

	public void setWarrantyInYears(Long warrantyInYears) {
		this.warrantyInYears = warrantyInYears;
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

	public String getAdditionalInfoOne() {
		return additionalInfoOne;
	}

	public void setAdditionalInfoOne(String additionalInfoOne) {
		this.additionalInfoOne = additionalInfoOne;
	}

	public String getAdditionalInfoTwo() {
		return additionalInfoTwo;
	}

	public void setAdditionalInfoTwo(String additionalInfoTwo) {
		this.additionalInfoTwo = additionalInfoTwo;
	}

}
