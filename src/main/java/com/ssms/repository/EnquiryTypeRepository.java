package com.ssms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssms.entity.EnquiryType;

@Repository
public interface EnquiryTypeRepository extends JpaRepository<EnquiryType, Long> {

}
