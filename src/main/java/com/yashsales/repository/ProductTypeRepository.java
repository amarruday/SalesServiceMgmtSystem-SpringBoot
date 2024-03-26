package com.yashsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

	@Query("FROM ProductType pt where pt.productCatagory.productCatagoryId = :productCatagoryId")
	public List<ProductType> findAllByProductCatagory(@Param(value = "productCatagoryId") Long productCatagoryId);

	@Query("FROM ProductType pt WHERE pt.status=true")
	public List<ProductType> findByStatus();
	
}
