package com.ssms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_types")
public class ProductType  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productTypeId;

	@OneToOne
	@JoinColumn(name = "productCatagoryId")
	private ProductCatagory productCatagory;
	private String productName;
	private String ticketPrefix;
	private boolean status;
	@JsonIgnore
	private String notes;
}
