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
@Table(name = "customerproductlink")
public class CustomerProductLink {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customerProductLinkId;

	@OneToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "productCatagoryId")
	private ProductCatagory productCatagory;

	@OneToOne
	@JoinColumn(name = "productTypeId")
	private ProductType productType;

	@OneToOne
	@JoinColumn(name = "brandId")
	private Brand brand;

	@OneToOne
	@JoinColumn(name = "productId")
	private Product product;

	private Timestamp dateOfPurchase;
	private Timestamp warrantyTill;
	private Long quantity;
	private String machineSerialNumber;
	private Timestamp recordDate;
	private String remark;

	public Long getCustomerProductLinkId() {
		return customerProductLinkId;
	}

	public void setCustomerProductLinkId(Long customerProductLinkId) {
		this.customerProductLinkId = customerProductLinkId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ProductCatagory getProductCatagory() {
		return productCatagory;
	}

	public void setProductCatagory(ProductCatagory productCatagory) {
		this.productCatagory = productCatagory;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Timestamp getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Timestamp dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public Timestamp getWarrantyTill() {
		return warrantyTill;
	}

	public void setWarrantyTill(Timestamp warrantyTill) {
		this.warrantyTill = warrantyTill;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getMachineSerialNumber() {
		return machineSerialNumber;
	}

	public void setMachineSerialNumber(String machineSerialNumber) {
		this.machineSerialNumber = machineSerialNumber;
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
