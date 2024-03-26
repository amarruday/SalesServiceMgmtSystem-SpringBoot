package com.yashsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	public Customer findByMobileNumber(String mobileNumber);

	@Query("FROM Customer c WHERE CONCAT(c.customerName, c.customerEmail, c.mobileNumber) LIKE %:inputString%")
	public List<Customer> searchCustomer(@Param(value = "inputString") String inputString);

	@Query("FROM Customer c WHERE c.customerType=:customerType")
	public List<Customer> getCustomersByType(@Param(value="customerType")String customerTypeCustomer);

}
