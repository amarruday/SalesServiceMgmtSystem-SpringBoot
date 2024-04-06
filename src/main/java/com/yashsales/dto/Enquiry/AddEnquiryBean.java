package com.yashsales.dto.Enquiry;

import java.sql.Timestamp;
import java.util.List;

import com.yashsales.dto.product.ProductInfo;
import com.yashsales.entity.Customer;
import com.yashsales.entity.Product;
import com.yashsales.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddEnquiryBean {
	private Long enquiryId;
	private Customer customer;
	private Long customerId;
	private Long enquirySourceId;
	private String enquirySourceName;
	private Long EnquiryTypeId;
	private String EnquiryTypeName;
	private List<ProductInfo> productList;
	private Product product;
	private Long productId;
	private String productName;
	private Long quantity;
	private String productRemark;
	private User addedBy;
	private String addedByName;
	private Long addedById;
	private boolean isSelfAssigned;
	private Long assignedToId;
	private Timestamp addedDate;
	private Timestamp recentActivityDate;
	private String remark;
	private String status;
	private Long createdBy;
	private Timestamp createdDate;
}
