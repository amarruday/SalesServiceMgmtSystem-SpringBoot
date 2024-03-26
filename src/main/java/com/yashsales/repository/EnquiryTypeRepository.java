package com.yashsales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.EnquiryType;

@Repository
public interface EnquiryTypeRepository extends JpaRepository<EnquiryType, Long> {

}
