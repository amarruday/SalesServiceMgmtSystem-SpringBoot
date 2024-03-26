package com.yashsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.ProductCatagory;

@Repository
public interface ProductCatagoryRepository extends JpaRepository<ProductCatagory, Long> {

	@Query("FROM ProductCatagory as pc WHERE pc.productCatagoryStatus=true")
	public List<ProductCatagory> findByProductCatagoryStatus();

}
