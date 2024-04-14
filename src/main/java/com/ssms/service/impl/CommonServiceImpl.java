package com.ssms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ssms.exceptions.CommonReplyAlreadyExistsException;
import org.springframework.stereotype.Service;

import com.ssms.constants.ApplicationConstants;
import com.ssms.dto.commons.RoleWiseManagersListBean;
import com.ssms.entity.CommonReply;
import com.ssms.entity.Department;
import com.ssms.entity.EnquirySource;
import com.ssms.entity.EnquiryType;
import com.ssms.entity.ProductCatagory;
import com.ssms.entity.Role;
import com.ssms.entity.UserProductCatagoryLink;
import com.ssms.repository.CommonReplyRepository;
import com.ssms.repository.DepartmentRepository;
import com.ssms.repository.EnquirySourceRepository;
import com.ssms.repository.EnquiryTypeRepository;
import com.ssms.repository.ProductCatagoryRepository;
import com.ssms.repository.RoleRepository;
import com.ssms.repository.UserProductCatagoryLinkRepository;
import com.ssms.service.CommonService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommonServiceImpl implements CommonService {

	private final RoleRepository roleRepo;
	private final ProductCatagoryRepository prodCatagoryRepo;
	private final UserProductCatagoryLinkRepository userProductCatagoryLinkRepo;
	private final CommonReplyRepository commonReplyRepo;
	private final EnquiryTypeRepository enqTypeRepo;
	private final EnquirySourceRepository enqSourceRepo;
	private final DepartmentRepository deptRepo;

	// Common Replys
	@Override
	public Map<String, Object> getCommonReplies() {
		List<CommonReply> commonRepliesList = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("CommonReplyList", null);
		commonRepliesList = commonReplyRepo.findAll();
		if (!commonRepliesList.isEmpty()) {
			responseMap.put("CommonReplyList", commonRepliesList);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> deleteCommonReply(Long commonReplyId) {
		CommonReply commonReply = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("message", "Failed to delete action type!");

		if (commonReplyId != null & commonReplyId > 0) {
			commonReply = commonReplyRepo.findById(commonReplyId).orElse(null);
			if (commonReply != null && commonReply.getCommonReplyId() > 0) {
				commonReplyRepo.delete(commonReply);
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseMap.put("message", "Common Reply deleted successfully!");
			}
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> addCommonReply(CommonReply commonReply) {
		List<CommonReply> commonReplyList = null;
		commonReplyList = commonReplyRepo.findAll();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Optional<CommonReply> duplicate = commonReplyList.stream().filter(commonReplyFilter -> commonReplyFilter
				.getCommonReply().trim().equalsIgnoreCase(commonReply.getCommonReply().trim())).findFirst();

		if (duplicate.isPresent()) {
			throw new CommonReplyAlreadyExistsException("This Reply text is already exists.");
		} else {
			commonReplyRepo.save(commonReply);
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("message", "Common Reply Text added.");
		}
		return responseMap;
	}

	// departments
	@Override
	public List<Department> getDepartments() {
		List<Department> departmentList = new ArrayList<Department>();
		departmentList = deptRepo.findAll();
		return departmentList;
	}

	// Enquiry Sources
	@Override
	public Map<String, Object> getEnquirySources() {
		List<EnquirySource> enqSourceList = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("EnquirySourcesList", null);
		enqSourceList = enqSourceRepo.findAll();
		if (enqSourceList != null && enqSourceList.size() > 0) {
			responseMap.put("EnquirySourcesList", enqSourceList);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> addEnquirySource(EnquirySource enquirySource) {
		List<EnquirySource> enquirySourcesList = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		enquirySourcesList = enqSourceRepo.findAll();

		Optional<EnquirySource> duplicate = enquirySourcesList.stream().filter(enqSource -> enqSource.getEnquirySource()
				.trim().equalsIgnoreCase(enquirySource.getEnquirySource().trim())).findFirst();

		if (duplicate.isPresent()) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
			responseMap.put("message", "This Enquiry Source is already exists!");
			responseMap.put("errorCode", "WSEM0003");
		} else {
			enqSourceRepo.save(enquirySource);
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("message", "Enquiry Source added Successfully!");
			responseMap.put("errorCode", "");
		}
		return responseMap;
	}

	// Enquiry Types
	@Override
	public Map<String, Object> getEnquiryTypes() {
		List<EnquiryType> enqTypeList = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("EnquiryTypeList", null);
		enqTypeList = enqTypeRepo.findAll();
		if (enqTypeList != null && enqTypeList.size() > 0) {
			responseMap.put("EnquiryTypeList", enqTypeList);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getRoles() {
		List<Role> roles = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("Roles", null);

		roles = roleRepo.findAll();
		if (roles != null && roles.size() > 0) {
			roles.removeIf(role -> role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_ADMIN));			
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("Roles", roles);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getSettingsByRole(Long roleId) {
		Role role = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (roleId > 0) {
			role = roleRepo.findById(roleId).get();
			if (role.getRoleId() > 0 && role != null) {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)
						|| role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)) {
					List<ProductCatagory> productCatagoryList = null;
					productCatagoryList = prodCatagoryRepo.findByProductCatagoryStatus();
					returnMap.put("ProductCatagoryList", productCatagoryList);
				}

				if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_ENGINEER)
						|| role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_ENGINEER)) {
					List<UserProductCatagoryLink> userProductCatagoryLinkList = null;
					List<RoleWiseManagersListBean> roleWiseManagersBeanList = new ArrayList<RoleWiseManagersListBean>();

					userProductCatagoryLinkList = userProductCatagoryLinkRepo.findAll();
					if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_ENGINEER)) {
						userProductCatagoryLinkList.forEach(userProd -> {
							if (userProd.getUser().getRole().getRolename()
									.equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)) {
								RoleWiseManagersListBean roleWiseManagersBean = new RoleWiseManagersListBean();
								roleWiseManagersBean.setManagerId(userProd.getUser().getUserId());
								String manager_ProductCatagory = userProd.getUser().getFullName() + " ["
										+ userProd.getUser().getRole().getRolename() + " of "
										+ userProd.getProductCatagory().getProductCatagoryName() + "]";
								roleWiseManagersBean.setManager_productCatagory(manager_ProductCatagory);
								roleWiseManagersBeanList.add(roleWiseManagersBean);
							}
						});
					}

					if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_ENGINEER)) {
						userProductCatagoryLinkList.forEach(userProd -> {
							if (userProd.getUser().getRole().getRolename()
									.equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)) {
								RoleWiseManagersListBean roleWiseManagersBean = new RoleWiseManagersListBean();
								roleWiseManagersBean.setManagerId(userProd.getUser().getUserId());
								String manager_ProductCatagory = userProd.getUser().getFullName() + " - "
										+ userProd.getProductCatagory().getProductCatagoryName();
								roleWiseManagersBean.setManager_productCatagory(manager_ProductCatagory);
								roleWiseManagersBeanList.add(roleWiseManagersBean);
							}
						});
					}
					returnMap.put("RoleWiseManagersList", roleWiseManagersBeanList);
				}
			}
		}
		return returnMap;
	}

}
