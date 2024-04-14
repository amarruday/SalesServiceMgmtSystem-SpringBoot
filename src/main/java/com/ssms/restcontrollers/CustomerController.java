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

import com.ssms.dto.customer.CustomerOutputBean;
import com.ssms.dto.customer.CustomerPagination;
import com.ssms.service.CustomerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/customer")
@CrossOrigin("*")
public class CustomerController {
	
	private final CustomerService customerService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> addCustomer(@RequestBody CustomerOutputBean custBean) {
		return new ResponseEntity<>(customerService.addCustomer(custBean), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/{customerId}")
	public ResponseEntity<Map<String, Object>> getCustomer(@PathVariable Long customerId) {
		return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);	
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/all")
	public ResponseEntity<Map<String, Object>> getAllCustomersList() {
		return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@PostMapping("/all")
	public ResponseEntity<Map<String, Object>> getAllCustomerListPaginated(@RequestBody CustomerPagination pagination) {
		return new ResponseEntity<>(customerService.getAllCustomersListPaginated(pagination), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllCustomerTypeCustomer() {
		return new ResponseEntity<>(customerService.getAllCustomerTypeCustomer(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> updateCustomer(@RequestBody CustomerOutputBean custBean) throws Exception {
		return new ResponseEntity<>(customerService.updateCustomer(custBean), HttpStatus.OK);
	}
	
	/*@GetMapping("/search/{mobileNumber}")
	public Map<String, Object> getCustomerByMobileNumber(@PathVariable String mobileNumber) {
		return custService.searchCustomerByMobileNumer(mobileNumber);
	}*/

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/search/{inputString}")
	public ResponseEntity<Map<String, Object>> searchCustomer(@PathVariable String inputString) {
		return new ResponseEntity<>(customerService.searchCustomer(inputString), HttpStatus.OK);
	}
	
}
