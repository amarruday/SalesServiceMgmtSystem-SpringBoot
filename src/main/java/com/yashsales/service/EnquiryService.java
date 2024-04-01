package com.yashsales.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yashsales.dto.AddEnquiryBean;
import com.yashsales.dto.EnquirySearchBean;
import com.yashsales.dto.Enquiry.AssignEnquiryBean;
import com.yashsales.dto.Enquiry.CancelEnquiryBean;
import com.yashsales.dto.Enquiry.ConvertToProspectBean;
import com.yashsales.dto.Enquiry.WonLostEnquiryBean;

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
