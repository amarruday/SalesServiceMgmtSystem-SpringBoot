package com.yashsales.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "user_product_catagory_link")
public class UserProductCatagoryLink {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@Column(name = "USER_PRODUCT_CATAGORY_LINK_ID")
	private Long userProductCatagoryLinkId;
		
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="productCatagoryId")
	private ProductCatagory productCatagory;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="userId")
	private User user;
	
	//@Column(name="STATUS")
	private boolean status;
	
	@CreationTimestamp
	//@Column(name="CREATED_DATE")
	private Timestamp createdDate;
	
	@CreatedBy
	//@Column(name="CREATED_BY")
	private String createdBy;
	
	@UpdateTimestamp
	//@Column(name="UPDATE_DATE")
	private Timestamp updateDate;
	
	//@Column(name="UPDATED_BY")
	private String updatedBy;

	public Long getUserProductCatagoryLinkId() {
		return userProductCatagoryLinkId;
	}

	public void setUserProductCatagoryLinkId(Long userProductCatagoryLinkId) {
		this.userProductCatagoryLinkId = userProductCatagoryLinkId;
	}

	public ProductCatagory getProductCatagory() {
		return productCatagory;
	}

	public void setProductCatagory(ProductCatagory productCatagory) {
		this.productCatagory = productCatagory;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
