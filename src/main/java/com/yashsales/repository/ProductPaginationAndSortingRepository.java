package com.yashsales.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.Product;

@Repository
public interface ProductPaginationAndSortingRepository extends CrudRepository<Product, Long> {
	//Page<Product> findAll(Pageable pageble);
}
