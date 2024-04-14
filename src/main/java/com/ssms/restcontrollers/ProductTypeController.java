package com.ssms.restcontrollers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssms.dto.product.ProductTypeOutputBean;
import com.ssms.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/producttype")
@CrossOrigin("*")
public class ProductTypeController {
	
	private final ProductService productService;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/productCatagory/{productCatagoryId}")
	public ResponseEntity<Map<String, Object>> getProductTypesByProductCatagoryId(@PathVariable Long productCatagoryId) {
		return new ResponseEntity<>(productService.getProductTypes(productCatagoryId), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getProductTypes() {
		return new ResponseEntity<>(productService.getProductTypes(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/Active")
	public ResponseEntity<Map<String, Object>> getActiveProductTypes() {
		return new ResponseEntity<>(productService.getActiveProductTypes(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/{productTypeId}")
	public ResponseEntity<Map<String, Object>> getProductTypeByProductTypeId(@PathVariable Long productTypeId) {
		System.out.println(productTypeId);
		return new ResponseEntity<>(productService.getProductTypeByProductTypeId(productTypeId), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> addProductType(@RequestBody ProductTypeOutputBean productTypeBean) {
		return new ResponseEntity<>(productService.addProductType(productTypeBean), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> updateProductType(@RequestBody ProductTypeOutputBean productTypeBean) {
		return new ResponseEntity<>(productService.updateProductType(productTypeBean), HttpStatus.OK);
	}
}
