package com.ssms.dto.product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBean {
	private Long productId;
	private Long productTypeId;
	private String productTypeName;

	private Long brandId;
	private String brandName;

	private String productName;
	private Long warrantyInYears;
	private String ticketPrefix;
	private boolean status;
}
