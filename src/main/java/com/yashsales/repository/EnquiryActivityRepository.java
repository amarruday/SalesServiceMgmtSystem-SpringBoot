package com.yashsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.Enquiry;
import com.yashsales.entity.EnquiryActivity;

@Repository
public interface EnquiryActivityRepository extends JpaRepository<EnquiryActivity, Long> {

	public List<EnquiryActivity> findByEnquiry(Enquiry enquiry);

}
