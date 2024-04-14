package com.ssms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssms.entity.CommonReply;
import com.ssms.entity.Department;
import com.ssms.entity.EnquirySource;

@Service
public interface CommonService {
	public Map<String, Object> getRoles();
	public Map<String, Object> getSettingsByRole(Long roleId);

	public Map<String, Object> getCommonReplies();
	public Map<String, Object> deleteCommonReply(Long commonReplyId);
	public Map<String, Object> addCommonReply(CommonReply commonReply);
	
	public List<Department> getDepartments();
	
	public Map<String, Object> getEnquiryTypes();
	public Map<String, Object> getEnquirySources();
	public Map<String, Object> addEnquirySource(EnquirySource enquirySource);
	
}
