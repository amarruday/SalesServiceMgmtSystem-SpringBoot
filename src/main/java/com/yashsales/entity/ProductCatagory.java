package com.yashsales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "product_catagory")
public class ProductCatagory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productCatagoryId;
	private String productCatagoryName;
	private boolean productCatagoryStatus;
}
