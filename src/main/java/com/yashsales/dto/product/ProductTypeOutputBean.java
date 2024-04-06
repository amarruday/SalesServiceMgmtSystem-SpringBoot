package com.yashsales.dto.product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeOutputBean {
	private Long productTypeId;
	private Long productCatagoryId;
	private String productName;
	private String ticketPrefix;
	private boolean status;
}
