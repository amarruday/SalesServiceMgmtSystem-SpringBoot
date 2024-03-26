package com.yashsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	@Query("FROM Product prod WHERE prod.productType.productTypeId =:productTypeId AND prod.brand.brandId =:brandId")
	List<Product> findAllProducts(@Param(value = "productTypeId") Long productTypeId, @Param(value = "brandId") Long brandId);

}
