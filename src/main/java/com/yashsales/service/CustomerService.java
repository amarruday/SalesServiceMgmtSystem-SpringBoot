package com.yashsales.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yashsales.outputbeans.CustomerOutputBean;
import com.yashsales.outputbeans.CustomerPagination;

@Service
public interface CustomerService {

	public Map<String, Object> addCustomer(CustomerOutputBean custBean);

	public Map<String, Object> getCustomerById(Long customerId);

	public Map<String, Object> updateCustomer(CustomerOutputBean custBean) throws Exception;

	public Map<String, Object> searchCustomerByMobileNumer(String mobileNumber);

	public Map<String, Object> searchCustomer(String inputString);
	
	public Map<String, Object> getAllCustomers();

	public Map<String, Object> getAllCustomerTypeCustomer();

	public Map<String, Object> getAllCustomersListPaginated(CustomerPagination pagination);

}
