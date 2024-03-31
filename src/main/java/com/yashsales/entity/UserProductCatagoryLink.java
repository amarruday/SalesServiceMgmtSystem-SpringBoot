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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
