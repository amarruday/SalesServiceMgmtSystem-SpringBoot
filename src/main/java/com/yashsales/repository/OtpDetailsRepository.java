package com.yashsales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.OtpDetails;

@Repository
public interface OtpDetailsRepository extends JpaRepository<OtpDetails, Long> {

}