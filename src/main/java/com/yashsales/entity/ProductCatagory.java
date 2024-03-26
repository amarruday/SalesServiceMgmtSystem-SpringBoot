package com.yashsales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_catagory")
public class ProductCatagory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productCatagoryId;
	private String productCatagoryName;
	private boolean productCatagoryStatus;

	public Long getProductCatagoryId() {
		return productCatagoryId;
	}

	public void setProductCatagoryId(Long productCatagoryId) {
		this.productCatagoryId = productCatagoryId;
	}

	public String getProductCatagoryName() {
		return productCatagoryName;
	}

	public void setProductCatagoryName(String productCatagoryName) {
		this.productCatagoryName = productCatagoryName;
	}

	public boolean isProductCatagoryStatus() {
		return productCatagoryStatus;
	}

	public void setProductCatagoryStatus(boolean productCatagoryStatus) {
		this.productCatagoryStatus = productCatagoryStatus;
	}
}
