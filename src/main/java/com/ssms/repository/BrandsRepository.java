package com.ssms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssms.entity.Brand;

@Repository
public interface BrandsRepository extends JpaRepository<Brand, Long>{

	@Query("FROM Brand b WHERE b.status=true")
	public List<Brand> findByStatus();
	
}
