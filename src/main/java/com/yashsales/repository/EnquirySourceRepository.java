package com.yashsales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.EnquirySource;

@Repository
public interface EnquirySourceRepository extends JpaRepository<EnquirySource, Long>{

}
