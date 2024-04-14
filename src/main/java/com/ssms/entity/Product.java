package com.ssms.entity;

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

}
