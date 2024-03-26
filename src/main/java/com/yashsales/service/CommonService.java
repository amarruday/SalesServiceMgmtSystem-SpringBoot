package com.yashsales.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yashsales.entity.Brand;
import com.yashsales.entity.CommonReply;
import com.yashsales.entity.Department;
import com.yashsales.entity.EnquirySource;

@Service
public interface CommonService {
	public Map<String, Object> getRoles();
	public Map<String, Object> getSettingsByRole(Long roleId);
	
	public Map<String, Object> getBrands();
	public Map<String, Object> getActiveBrands();
	public Map<String, Object> getBrand(Long brandId);
	public Map<String, Object> addBrand(Brand brand);
	public Map<String, Object> editBrand(Brand brand);
		
	public Map<String, Object> getCommonReplies();
	public Map<String, Object> deleteCommonReply(Long commonReplyId);
	public Map<String, Object> addCommonReply(CommonReply commonReply);
	
	public List<Department> getDepartments();
	
	public Map<String, Object> getEnquiryTypes();
	public Map<String, Object> getEnquirySources();
	public Map<String, Object> addEnquirySource(EnquirySource enquirySource);
	
}
