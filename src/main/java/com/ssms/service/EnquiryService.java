package com.ssms.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssms.dto.Enquiry.AddEnquiryBean;
import com.ssms.dto.Enquiry.EnquirySearchBean;
import com.ssms.dto.Enquiry.AssignEnquiryBean;
import com.ssms.dto.Enquiry.CancelEnquiryBean;
import com.ssms.dto.Enquiry.ConvertToProspectBean;
import com.ssms.dto.Enquiry.WonLostEnquiryBean;

@Service
public interface EnquiryService {
	public abstract Map<String, Object> addEnquiry(AddEnquiryBean enquiryBean);
	public abstract Map<String, Object> getPaginatedEnquiries(EnquirySearchBean searchBean);
	public abstract Map<String, Object> getAssignedToList();
	public abstract Map<String, Object> getEnquiryDetails(Long enquiryId);
	public abstract Map<String, Object> assignEnquiry(AssignEnquiryBean assignEnquiry);
	public abstract Map<String, Object> cancelEnquiry(CancelEnquiryBean cancelEnquiry);
	public abstract Map<String, Object> convertToProspectEnquiry(ConvertToProspectBean convertToProspect);
	public abstract Map<String, Object> wonEnquiry(WonLostEnquiryBean wonEnquiry);
	public abstract Map<String, Object> lostEnquiry(WonLostEnquiryBean lostEnquiry);
}
