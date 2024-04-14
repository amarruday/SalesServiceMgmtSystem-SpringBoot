package com.ssms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssms.entity.EnquirySource;

@Repository
public interface EnquirySourceRepository extends JpaRepository<EnquirySource, Long>{

}
