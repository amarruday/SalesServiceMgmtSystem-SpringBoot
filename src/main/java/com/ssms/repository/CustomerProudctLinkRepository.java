package com.ssms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssms.entity.Customer;
import com.ssms.entity.CustomerProductLink;

@Repository
public interface CustomerProudctLinkRepository extends JpaRepository<CustomerProductLink, Long>{
	@Query("FROM CustomerProductLink at WHERE at.customer.customerId =:customerId AND at.product.productId=:productId")
	public abstract List<CustomerProductLink> findAllByCustomerAndProduct(@Param(value="customerId") Long customerId, @Param(value="productId") Long productId);

	public abstract List<CustomerProductLink> findByCustomer(Customer customer);
}
