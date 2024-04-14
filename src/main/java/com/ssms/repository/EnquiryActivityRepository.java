package com.ssms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssms.entity.Enquiry;
import com.ssms.entity.EnquiryActivity;

@Repository
public interface EnquiryActivityRepository extends JpaRepository<EnquiryActivity, Long> {

	public List<EnquiryActivity> findByEnquiry(Enquiry enquiry);

}
