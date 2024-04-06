package com.yashsales.dto.product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
	private Long productId;
	private String productName;
	private Long quantity;
	private String productRemark;
}
