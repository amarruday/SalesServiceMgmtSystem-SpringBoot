package com.ssms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ssms.entity.Product;

@Repository
public interface ProductPaginationAndSortingRepository extends CrudRepository<Product, Long> {
	//Page<Product> findAll(Pageable pageble);
}
