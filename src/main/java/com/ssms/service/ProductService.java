package com.ssms.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssms.dto.product.ProductBean;
import com.ssms.dto.product.ProductTypeOutputBean;
import com.ssms.dto.product.SearchProductPagniationOutputbean;

@Service
public interface ProductService {

	Map<String, Object> getProductTypes(Long productCatagoryId);
	Map<String, Object> getProductTypes();
	Map<String, Object> getProductTypeByProductTypeId(Long productTypeId);
	Map<String, Object> addProductType(ProductTypeOutputBean productTypeBean);
	Map<String, Object> updateProductType(ProductTypeOutputBean productTypeBean);
	Map<String, Object> getPagniatedProductsList(SearchProductPagniationOutputbean searchProductBean);
	Map<String, Object> getActiveProductTypes();
	Map<String, Object> getProductByProductId(Long productId);
	Map<String, Object> addProduct(ProductBean productBean);
	Map<String, Object> updateProduct(ProductBean productBean);
	Map<String, Object> deleteProduct(Long productId);
	Map<String, Object> getProductTypesByAssignedProductCatagory(Long userId);
	Map<String, Object> getProductByBrandIdAndProductTypeId(Long brandId, Long productTypeId);
}
