package com.ssms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssms.entity.OtpDetails;

@Repository
public interface OtpDetailsRepository extends JpaRepository<OtpDetails, Long> {

}