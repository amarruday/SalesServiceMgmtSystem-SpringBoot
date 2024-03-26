package com.yashsales.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.entity.Brand;
import com.yashsales.entity.CommonReply;
import com.yashsales.entity.Department;
import com.yashsales.entity.EnquirySource;
import com.yashsales.entity.EnquiryType;
import com.yashsales.entity.ProductCatagory;
import com.yashsales.entity.Role;
import com.yashsales.entity.UserProductCatagoryLink;
import com.yashsales.outputbeans.RoleWiseManagersListBean;
import com.yashsales.repository.BrandsRepository;
import com.yashsales.repository.CommonReplyRepository;
import com.yashsales.repository.DepartmentRepository;
import com.yashsales.repository.EnquirySourceRepository;
import com.yashsales.repository.EnquiryTypeRepository;
import com.yashsales.repository.ProductCatagoryRepository;
import com.yashsales.repository.RoleRepository;
import com.yashsales.repository.UserProductCatagoryLinkRepository;
import com.yashsales.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ProductCatagoryRepository prodCatagoryRepo;

	@Autowired
	private UserProductCatagoryLinkRepository userProductCatagoryLinkRepo;

	@Autowired
	private BrandsRepository brandRepo;

	@Autowired
	private CommonReplyRepository commonReplyRepo;

	@Autowired
	private EnquiryTypeRepository enqTypeRepo;

	@Autowired
	private EnquirySourceRepository enqSourceRepo;

	@Autowired
	private DepartmentRepository deptRepo;

	// brand
	@Override
	public Map<String, Object> addBrand(Brand brand) {
		List<Brand> brandsList = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		brandsList = brandRepo.findAll();

		Optional<Brand> duplicate = brandsList.stream()
				.filter(brd -> brd.getBrandName().trim().equalsIgnoreCase(brand.getBrandName().trim())).findFirst();

		if (duplicate.isPresent()) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
			responseMap.put("message", "This Brand is already exists!");
			responseMap.put("errorCode", "WSEM0004");
		} else {
			brand.setStatus(true);
			brandRepo.save(brand);
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("message", "Brand is added successfully!");
			responseMap.put("errorCode", "");
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getActiveBrands() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Brand> brandsList = null;
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("BrandsList", null);
		
		brandsList = brandRepo.findByStatus();
		responseMap.put("BrandsList", brandsList);
		return responseMap;
	}
	
	@Override
	public Map<String, Object> editBrand(Brand brand) {
		List<Brand> brandsList = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		brandsList = brandRepo.findAll();

		Brand theLocalBrand = null;
		theLocalBrand = brandRepo.getById(brand.getBrandId());

		Optional<Brand> duplicate = brandsList.stream()
				.filter(brd -> brd.getBrandName().trim().equalsIgnoreCase(brand.getBrandName().trim())).findFirst();

		if (duplicate.isPresent()) {
			if (duplicate.get().getBrandId().equals(brand.getBrandId())) {
				theLocalBrand.setStatus(brand.isStatus());
				brandRepo.save(brand);
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseMap.put("message", "Brand is updated successfully!");
				responseMap.put("errorCode", "");
			} else {
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
				responseMap.put("message", "This Brand is already exists!");
				responseMap.put("errorCode", "WSEM0004");
			}
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getBrands() {
		List<Brand> brandsList = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("BrandsList", null);

		brandsList = brandRepo.findAll();
		if (brandsList != null && brandsList.size() > 0) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("BrandsList", brandsList);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getBrand(Long brandId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("Brand", null);

		Brand brand = brandRepo.findById(brandId).get();
		if (brand != null && brand.getBrandId() > 0) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("Brand", brand);
		}
		return responseMap;
	}

	// Common Replys
	@Override
	public Map<String, Object> getCommonReplies() {
		List<CommonReply> commonRepliesList = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("CommonReplyList", null);
		commonRepliesList = commonReplyRepo.findAll();
		if (commonRepliesList.size() > 0) {
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
			responseMap.put("message", "This Reply text is already exists.");
			responseMap.put("errorCode", "WSEC002");
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
			roles.removeIf(role -> role.getrolename().equals(ApplicationConstants.RoleConstants.ROLE_ADMIN));			
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
				if (role.getrolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)
						|| role.getrolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)) {
					List<ProductCatagory> productCatagoryList = null;
					productCatagoryList = prodCatagoryRepo.findByProductCatagoryStatus();
					returnMap.put("ProductCatagoryList", productCatagoryList);
				}

				if (role.getrolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_ENGINEER)
						|| role.getrolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_ENGINEER)) {
					List<UserProductCatagoryLink> userProductCatagoryLinkList = null;
					List<RoleWiseManagersListBean> roleWiseManagersBeanList = new ArrayList<RoleWiseManagersListBean>();

					userProductCatagoryLinkList = userProductCatagoryLinkRepo.findAll();
					if (role.getrolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_ENGINEER)) {
						userProductCatagoryLinkList.forEach(userProd -> {
							if (userProd.getUser().getRole().getrolename()
									.equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)) {
								RoleWiseManagersListBean roleWiseManagersBean = new RoleWiseManagersListBean();
								roleWiseManagersBean.setManagerId(userProd.getUser().getUserId());
								String manager_ProductCatagory = userProd.getUser().getFullName() + " ["
										+ userProd.getUser().getRole().getrolename() + " of "
										+ userProd.getProductCatagory().getProductCatagoryName() + "]";
								roleWiseManagersBean.setManager_productCatagory(manager_ProductCatagory);
								roleWiseManagersBeanList.add(roleWiseManagersBean);
							}
						});
					}

					if (role.getrolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_ENGINEER)) {
						userProductCatagoryLinkList.forEach(userProd -> {
							if (userProd.getUser().getRole().getrolename()
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
