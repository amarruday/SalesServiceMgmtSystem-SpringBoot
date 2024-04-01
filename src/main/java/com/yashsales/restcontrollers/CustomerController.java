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

import com.yashsales.outputbeans.CustomerOutputBean;
import com.yashsales.outputbeans.CustomerPagination;
import com.yashsales.service.CustomerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/customer")
@CrossOrigin("*")
public class CustomerController {
	
	private final CustomerService custService;
	
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> addCustomer(@RequestBody CustomerOutputBean custBean) {
		return new ResponseEntity<>(custService.addCustomer(custBean), HttpStatus.CREATED);
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<Map<String, Object>> getCustomer(@PathVariable Long customerId) {
		return new ResponseEntity<>(custService.getCustomerById(customerId), HttpStatus.OK);	
	}
	
	@GetMapping("/all")
	public ResponseEntity<Map<String, Object>> getAllCustomersList() {
		return new ResponseEntity<>(custService.getAllCustomers(), HttpStatus.OK);
	}
	
	@PostMapping("/all")
	public ResponseEntity<Map<String, Object>> getAllCustomerListPaginated(@RequestBody CustomerPagination pagination) {
		return new ResponseEntity<>(custService.getAllCustomersListPaginated(pagination), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllCustomerTypeCustomer() {
		return new ResponseEntity<>(custService.getAllCustomerTypeCustomer(), HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> updateCustomer(@RequestBody CustomerOutputBean custBean) throws Exception {
		return new ResponseEntity<>(custService.updateCustomer(custBean), HttpStatus.OK);
	}
	
	/*@GetMapping("/search/{mobileNumber}")
	public Map<String, Object> getCustomerByMobileNumber(@PathVariable String mobileNumber) {
		return custService.searchCustomerByMobileNumer(mobileNumber);
	}*/
	
	@GetMapping("/search/{inputString}")
	public ResponseEntity<Map<String, Object>> searchCustomer(@PathVariable String inputString) {
		return new ResponseEntity<>(custService.searchCustomer(inputString), HttpStatus.OK);
	}
	
}
