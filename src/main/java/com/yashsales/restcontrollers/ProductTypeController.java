package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.dto.ProductTypeOutputBean;
import com.yashsales.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/producttype")
@CrossOrigin("*")
public class ProductTypeController {
	
	private final ProductService productService;
	
	@GetMapping("/productCatagory/{productCatagoryId}")
	public ResponseEntity<Map<String, Object>> getProductTypesByProductCatagoryId(@PathVariable Long productCatagoryId) {
		return new ResponseEntity<>(productService.getProductTypes(productCatagoryId), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getProductTypes() {
		return new ResponseEntity<>(productService.getProductTypes(), HttpStatus.OK);
	}
	
	@GetMapping("/Active")
	public ResponseEntity<Map<String, Object>> getActiveProductTypes() {
		return new ResponseEntity<>(productService.getActiveProductTypes(), HttpStatus.OK);
	}
	
	@GetMapping("/{productTypeId}")
	public ResponseEntity<Map<String, Object>> getProductTypeByProductTypeId(@PathVariable Long productTypeId) {
		System.out.println(productTypeId);
		return new ResponseEntity<>(productService.getProductTypeByProductTypeId(productTypeId), HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> addProductType(@RequestBody ProductTypeOutputBean productTypeBean) {
		return new ResponseEntity<>(productService.addProductType(productTypeBean), HttpStatus.CREATED);
	}
	
	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> updateProductType(@RequestBody ProductTypeOutputBean productTypeBean) {
		return new ResponseEntity<>(productService.updateProductType(productTypeBean), HttpStatus.OK);
	}
}
