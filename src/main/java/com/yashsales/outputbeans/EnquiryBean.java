package com.yashsales.outputbeans;

import java.sql.Timestamp;
import java.util.List;

import com.yashsales.entity.Customer;
import com.yashsales.entity.Product;
import com.yashsales.entity.User;

public class EnquiryBean {

	private Long enquiryId;
	private Customer customer;
	private Long customerId;

	private Long enquirySourceId;
	private String enquirySourceName;

	private Long EnquiryTypeId;
	private String EnquiryTypeName;

	private List<Product> productList;
	private Product product;
	private Long productId;
	private String productName;
	private Long quantity;
	private String productRemark;

	private User addedBy;
	private String addedByName;

	private Timestamp addedDate;
	private Timestamp recentActivityDate;
	private String remark;
	private String status;
	private Long createdBy;
	private Timestamp createdDate;
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
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getEnquirySourceId() {
		return enquirySourceId;
	}
	public void setEnquirySourceId(Long enquirySourceId) {
		this.enquirySourceId = enquirySourceId;
	}
	public String getEnquirySourceName() {
		return enquirySourceName;
	}
	public void setEnquirySourceName(String enquirySourceName) {
		this.enquirySourceName = enquirySourceName;
	}
	public Long getEnquiryTypeId() {
		return EnquiryTypeId;
	}
	public void setEnquiryTypeId(Long enquiryTypeId) {
		EnquiryTypeId = enquiryTypeId;
	}
	public String getEnquiryTypeName() {
		return EnquiryTypeName;
	}
	public void setEnquiryTypeName(String enquiryTypeName) {
		EnquiryTypeName = enquiryTypeName;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public String getAddedByName() {
		return addedByName;
	}
	public void setAddedByName(String addedByName) {
		this.addedByName = addedByName;
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

}
