package com.yashsales.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.entity.Customer;
import com.yashsales.outputbeans.CustomerOutputBean;
import com.yashsales.outputbeans.CustomerPagination;
import com.yashsales.repository.CustomerRepository;
import com.yashsales.service.CustomerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository custRepo;

	@Override
	public Map<String, Object> addCustomer(CustomerOutputBean custBean) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		if (custBean != null) {
			Customer customer = custRepo.findByMobileNumber(custBean.getMobileNumber());
			responseMap.put("Customer", null);
			if (customer != null && customer.getCustomerId() > 0) {
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
				responseMap.put("message", "This mobile number is already belongs to exsting customer.");
				responseMap.put("errorCode", "This mobile number is already belongs to exsting customer.");
			} else {
				customer = new Customer();
				customer.setCustomerName(custBean.getCustomerName());
				customer.setCustomerEmail(custBean.getCustomerEmail());
				customer.setMobileNumber(custBean.getMobileNumber());
				customer.setAddress(custBean.getAddress());
				customer.setCustomerType(ApplicationConstants.CustomerConstants.CUSTOMER_TYPE_PROSPECT);
				customer.setSendSms(false);
				customer.setSendEmail(false);
				customer.setBirthDate(custBean.getBirthDate());
				customer.setStatus(ApplicationConstants.CustomerConstants.CUSTOMER_STATUS_ACTIVE);
				customer = custRepo.save(customer);
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseMap.put("message", "Customer Added Successfully.");
				responseMap.put("Customer", customer);
			}
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> updateCustomer(CustomerOutputBean custBean) throws Exception {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		
		Customer customer = custRepo.findById(custBean.getCustomerId()).get();
		
		if(customer.getCustomerId() > 0) {
			customer.setCustomerName(custBean.getCustomerName());
			customer.setCustomerEmail(custBean.getCustomerEmail());
			//Here mobile number validation remaining
			customer.setCustomerType(custBean.getCustomerType());
			//customer.setSendSms(custBean.isSendSms());
			//customer.setSendEmail(custBean.isSendEmail());
			//customer.setBirthDate(custBean.getBirthDate());
			customer.setStatus(custBean.getStatus());
			customer = custRepo.save(customer);
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("message", "Customer Updated Successfully.");
			responseMap.put("Customer", customer);
		}
		return responseMap;
	}

	public Map<String, Object> deleteCustomer(Long customerId) {
		return null;
	}

	@Override
	public Map<String, Object> getCustomerById(Long customerId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		Customer customer = custRepo.findById(customerId).get();
		responseMap.put("Customer", null);
		if (customer != null && customer.getCustomerId() > 0) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("Customer", customer);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> searchCustomerByMobileNumer(String mobileNumber) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("responseMap", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("Customer", null);
		Customer customer = custRepo.findByMobileNumber(mobileNumber);
		if(customer.getCustomerId() > 0) {
			responseMap.put("responseMap", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("Customer", customer);
		}
		return responseMap;
	}
	
	@Override
	public Map<String, Object> searchCustomer(String inputString) {
		List<Customer> customerList = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("CustomerList", null);
		
		if(inputString != null) {
			customerList = custRepo.searchCustomer(inputString);
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("CustomerList", customerList);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getAllCustomers() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Customer> customerList = null;
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("CustomerList", null);
		customerList = custRepo.findAll();
		if (customerList != null && customerList.size()>0) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("CustomerList", customerList);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getAllCustomerTypeCustomer() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Customer> customerList = null;
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("CustomerList", null);
		customerList = custRepo.getCustomersByType(ApplicationConstants.CustomerConstants.CUSTOMER_TYPE_CUSTOMER);
		if (customerList != null && customerList.size()>0) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("CustomerList", customerList);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getAllCustomersListPaginated(CustomerPagination pagination) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Page<Customer> customerPage = null;
		
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("CustomerList", null);
		
		int pageNumber = Math.subtractExact(pagination.getPageNumber(), 1);
		Pageable pagable = PageRequest.of(pageNumber, pagination.getPageSize());
		
		customerPage = custRepo.findAll(pagable);
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("CustomerList", customerPage.getContent());
		responseMap.put("CurrentPage", customerPage.getNumber() + 1);
		responseMap.put("TotalItems", customerPage.getTotalElements());
		responseMap.put("TotalPages", customerPage.getTotalPages());
		
		return responseMap;
	}
}
