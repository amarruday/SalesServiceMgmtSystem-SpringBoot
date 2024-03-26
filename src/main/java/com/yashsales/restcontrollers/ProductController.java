package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.outputbeans.ProductBean;
import com.yashsales.outputbeans.SearchProductPagniationOutputbean;
import com.yashsales.service.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/get")
	public ResponseEntity<Map<String, Object>> getProducts(@RequestBody SearchProductPagniationOutputbean productBean) {		
		return new ResponseEntity<>(productService.getPagniatedProductsList(productBean), HttpStatus.OK); 
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Map<String, Object>> getProductByProductId(@PathVariable Long productId) {
		return new ResponseEntity<>(productService.getProductByProductId(productId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long productId) {
		return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Map<String, Object>> addProduct(@RequestBody ProductBean productBean) {
		return new ResponseEntity<>(productService.addProduct(productBean), HttpStatus.CREATED);
	}
	
	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> updateProduct(@RequestBody ProductBean productBean) {
		return new ResponseEntity<>(productService.updateProduct(productBean), HttpStatus.OK);
	}
	
	
	@GetMapping("/getproductTypesByAssigedCatagory/{userId}")
	public ResponseEntity<Map<String, Object>> getProductTypeByAssignedProductCatagory(@PathVariable Long userId) {
		return new ResponseEntity<>(productService.getProductTypesByAssignedProductCatagory(userId), HttpStatus.OK);
	}
	
	
	@GetMapping( "/getproducts/{brandId}/{productTypeId}")
	public ResponseEntity<Map<String, Object>> getProductByBrandIdAndProductTypeId(@PathVariable Long brandId, @PathVariable Long productTypeId) {
		return new ResponseEntity<>(productService.getProductByBrandIdAndProductTypeId(brandId, productTypeId), HttpStatus.OK);
	}
}
