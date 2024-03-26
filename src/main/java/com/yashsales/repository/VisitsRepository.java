package com.yashsales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.Visit;

@Repository
public interface VisitsRepository extends JpaRepository<Visit, Long>, JpaSpecificationExecutor<Visit> {
	
}
