package com.yashsales.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductPagniationOutputbean {
	private int pageNumber;
	private int pageSize;
	private Long productTypeId = 0L;
	private Long brandId = 0L;
}
