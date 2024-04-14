package com.ssms.restcontrollers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssms.dto.product.ProductBean;
import com.ssms.dto.product.SearchProductPagniationOutputbean;
import com.ssms.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/product")
@CrossOrigin("*")
public class ProductController {
	
	private final ProductService productService;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@PostMapping("/get")
	public ResponseEntity<Map<String, Object>> getProducts(@RequestBody SearchProductPagniationOutputbean productBean) {		
		return new ResponseEntity<>(productService.getPagniatedProductsList(productBean), HttpStatus.OK); 
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/{productId}")
	public ResponseEntity<Map<String, Object>> getProductByProductId(@PathVariable Long productId) {
		return new ResponseEntity<>(productService.getProductByProductId(productId), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{productId}")
	public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long productId) {
		return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> addProduct(@RequestBody ProductBean productBean) {
		return new ResponseEntity<>(productService.addProduct(productBean), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> updateProduct(@RequestBody ProductBean productBean) {
		return new ResponseEntity<>(productService.updateProduct(productBean), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/getproductTypesByAssigedCatagory/{userId}")
	public ResponseEntity<Map<String, Object>> getProductTypeByAssignedProductCatagory(@PathVariable Long userId) {
		return new ResponseEntity<>(productService.getProductTypesByAssignedProductCatagory(userId), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping( "/getproducts/{brandId}/{productTypeId}")
	public ResponseEntity<Map<String, Object>> getProductByBrandIdAndProductTypeId(@PathVariable Long brandId, @PathVariable Long productTypeId) {
		return new ResponseEntity<>(productService.getProductByBrandIdAndProductTypeId(brandId, productTypeId), HttpStatus.OK);
	}
}
