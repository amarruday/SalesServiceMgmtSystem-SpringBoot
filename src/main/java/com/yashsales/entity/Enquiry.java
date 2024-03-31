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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	
}
