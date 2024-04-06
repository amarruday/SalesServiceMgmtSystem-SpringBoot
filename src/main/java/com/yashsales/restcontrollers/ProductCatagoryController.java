package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.service.ProductCatagoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/productcatagory")
@CrossOrigin("*")
public class ProductCatagoryController {

	private final ProductCatagoryService prodCatagoryService;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getProductCatagories() {
		return new ResponseEntity<>(prodCatagoryService.getProductCatagories(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/active")
	public ResponseEntity<Map<String, Object>> getActiveProductCatagories() {
		return new ResponseEntity<>(prodCatagoryService.getActiveProductCatagories(), HttpStatus.OK);
	}
}
