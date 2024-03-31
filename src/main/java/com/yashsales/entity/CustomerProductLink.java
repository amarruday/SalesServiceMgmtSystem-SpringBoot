package com.yashsales.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
