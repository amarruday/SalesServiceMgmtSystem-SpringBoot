package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.service.ProductCatagoryService;

@RestController
@RequestMapping("/productcatagory")
@CrossOrigin("*")
public class ProductCatagoryController {
	
	@Autowired
	private ProductCatagoryService prodCatagoryService;
	
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getProductCatagories() {
		return new ResponseEntity<>(prodCatagoryService.getProductCatagories(), HttpStatus.OK);
	}
	
	@GetMapping("/active")
	public ResponseEntity<Map<String, Object>> getActiveProductCatagories() {
		return new ResponseEntity<>(prodCatagoryService.getActiveProductCatagories(), HttpStatus.OK);
	}
}
