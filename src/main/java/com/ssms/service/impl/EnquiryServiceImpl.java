package com.ssms.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ssms.constants.ApplicationConstants;
import com.ssms.dto.Enquiry.AddEnquiryBean;
import com.ssms.dto.Enquiry.EnquirySearchBean;
import com.ssms.dto.Enquiry.AssignEnquiryBean;
import com.ssms.dto.Enquiry.CancelEnquiryBean;
import com.ssms.dto.Enquiry.ConvertToProspectBean;
import com.ssms.dto.Enquiry.WonLostEnquiryBean;
import com.ssms.entity.Customer;
import com.ssms.entity.CustomerProductLink;
import com.ssms.entity.Department;
import com.ssms.entity.Enquiry;
import com.ssms.entity.EnquiryActivity;
import com.ssms.entity.EnquirySource;
import com.ssms.entity.EnquiryType;
import com.ssms.entity.Product;
import com.ssms.entity.ProductType;
import com.ssms.entity.User;
import com.ssms.entity.UserProductCatagoryLink;
import com.ssms.repository.CustomerProudctLinkRepository;
import com.ssms.repository.CustomerRepository;
import com.ssms.repository.DepartmentRepository;
import com.ssms.repository.EnquiryActivityRepository;
import com.ssms.repository.EnquiryRepository;
import com.ssms.repository.EnquirySourceRepository;
import com.ssms.repository.EnquiryTypeRepository;
import com.ssms.repository.ProductRepository;
import com.ssms.repository.ProductTypeRepository;
import com.ssms.repository.UserProductCatagoryLinkRepository;
import com.ssms.repository.UserRepository;
import com.ssms.service.EnquiryService;
import com.ssms.service.UserService;
import com.ssms.utility.DateUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnquiryServiceImpl implements EnquiryService {

	private final EnquiryRepository enqRepo;
	private final CustomerRepository custRepo;
	private final EnquiryActivityRepository enqActivityRepo;
	private final EnquirySourceRepository enqSourceRepo;
	private final EnquiryTypeRepository enqTypeRepo;
	private final ProductRepository productRepo;
	private final UserRepository userRepo;
	private final UserProductCatagoryLinkRepository userProductCatagoryLinkRepo;
	private final ProductTypeRepository productTypeRepo;
	private final DepartmentRepository deptRepo;
	private final UserService userService;
	private final CustomerRepository customerRepo;
	private final CustomerProudctLinkRepository custProductLinkRepo;
	
	@Override
	@Transactional
	public Map<String, Object> addEnquiry(AddEnquiryBean enquiryBean) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Customer customer = null;
		Enquiry enquiry = null;
		EnquirySource enquirySource = null;
		EnquiryType enquiryType = null;
		Product product = null;
		User addedBy = null;

		if (enquiryBean.getCustomerId() > 0) {
			customer = custRepo.findById(enquiryBean.getCustomerId()).orElse(null);
		}

		if (customer != null && customer.getCustomerId() > 0) {
			if (enquiryBean.getProductList() != null && enquiryBean.getProductList().size() > 0) {
				for (int i = 0; i < enquiryBean.getProductList().size(); i++) {

					Long productId = enquiryBean.getProductList().get(i).getProductId();
					if (productId > 0) {

						enquiry = new Enquiry();
						// Set Customer
						enquiry.setCustomer(customer);

						// set Enquiry Source
						if (enquiryBean.getEnquirySourceId() > 0) {
							enquirySource = enqSourceRepo.findById(enquiryBean.getEnquirySourceId()).orElse(null);
							if (enquirySource != null) {
								enquiry.setEnquirySource(enquirySource);
							}
						}

						if (enquiryBean.getEnquiryTypeId() > 0) {
							enquiryType = enqTypeRepo.findById(enquiryBean.getEnquiryTypeId()).orElse(null);
							if (enquiryType != null) {
								enquiry.setEnquiryType(enquiryType);
							}
						}

						product = null;
						product = productRepo.findById(productId).orElse(null);
						if (product != null) {
							enquiry.setProduct(product);
							enquiry.setQuantity(enquiryBean.getProductList().get(i).getQuantity());
							enquiry.setProductRemark(enquiryBean.getProductList().get(i).getProductRemark());
						}

						// addedBy 
						addedBy  = userService.getCurrentLoggedInUser();
						//addedBy = userRepo.findById(enquiryBean.getAddedById()).orElse(null); //TODO Old code.
						enquiry.setAddedBy(addedBy);

						// addedDate
						enquiry.setAddedDate(DateUtils.getCurrentTimestamp());

						// status
						enquiry.setStatus(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_UNASSIGNED);

						if (enquiryBean.isSelfAssigned()) {
							enquiry.setAssignedTo(addedBy);
							enquiry.setAssignedBy(addedBy);
							enquiry.setStatus(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_ASSIGNED);
						} else if (enquiryBean.getAssignedToId() != null && enquiryBean.getAssignedToId() > 0) {
							User assignedTo = userRepo.findById(enquiryBean.getAssignedToId()).orElse(null);
							enquiry.setAssignedTo(assignedTo);
							enquiry.setAssignedBy(addedBy);
							enquiry.setStatus(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_ASSIGNED);
						}

						// recent activity date
						//enquiry.setRecentActivityDate(DateUtils.getCurrentTimestamp());
						enquiry.setRecentActivityDate(new Timestamp(System.currentTimeMillis()));

						// remark
						enquiry.setRemark(enquiryBean.getRemark());

						// createdBy
						enquiry.setCreatedBy(addedBy.getUserId());

						// createdDate
						enquiry.setCreatedDate(DateUtils.getCurrentTimestamp());

						// save should be here

						enquiry = enqRepo.save(enquiry);

						setEnquiryActivities(enquiry);
					} // NewProduct

				} // for
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				returnMap.put("message", "Enquriy added Successfully.");
			} else {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
				returnMap.put("message", "At least 1 product should be added for enquiry!");
				returnMap.put("errorCode", "WSEM008");
			}
		}
		return returnMap;
	}

	private void setEnquiryActivities(Enquiry enquiry) {
		String remark = "Enquiry is Unassigned.";

		if (enquiry.getEnquiryId() > 0) {
			EnquiryActivity enquiryActivity = new EnquiryActivity();
			enquiryActivity.setEnquiry(enquiry);
			enquiryActivity.setUser(enquiry.getAddedBy());
			enquiryActivity.setStatus(enquiry.getStatus());
			enquiryActivity.setRecordDate(enquiry.getRecentActivityDate());

			if (enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_ASSIGNED)) {
				remark = "Enquiry is Assigned.";
			}

			if (enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_CANCELLED)) {
				remark = "Enquiry is Cancelled.";
			}

			if (enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_LOST)) {
				remark = "Enquiry is Lost.";
			}

			if (enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_PROSPECT)) {
				remark = "Enquiry is in the Prospect.";
			}

			if (enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_UNASSIGNED)) {
				remark = "Enquiry is Unassigned.";
			}

			if (enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_WON)) {
				remark = "Enquiry is Won.";
			}
			enquiryActivity.setRemark(remark);
			enqActivityRepo.save(enquiryActivity);
		}
	}

	@Override
	public Map<String, Object> getPaginatedEnquiries(EnquirySearchBean searchBean) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Page<Enquiry> enquiryPage = null;
		int pageNumber = Math.subtractExact(searchBean.getPageNumber(), 1);
		Pageable pagable = PageRequest.of(pageNumber, searchBean.getPageSize());
		
		// Temp Code
		//User user = userRepo.findById(searchBean.getSysUserId()).orElse(null); //TODO change after Rolewise
		User user = userService.getCurrentLoggedInUser();
		
		LocalDate currentDate = LocalDate.now();
		if (searchBean.getStartDate() == null) {
			LocalDate startDate = currentDate.minus(1, ChronoUnit.WEEKS);
			Date date = Date.valueOf(startDate);
			searchBean.setStartDate(date);
		}

		if (searchBean.getEndDate() == null) {
			Date date = Date.valueOf(currentDate);
			searchBean.setEndDate(date);
		}

		Calendar s1 = Calendar.getInstance();
		s1.setTimeInMillis(searchBean.getStartDate().getTime());
		s1.set(Calendar.HOUR_OF_DAY, 0);
		s1.set(Calendar.MINUTE, 0);
		s1.set(Calendar.SECOND, 0);
		s1.set(Calendar.MILLISECOND, 0);
		searchBean.setStartDate(s1.getTime());

		Calendar e1 = Calendar.getInstance();
		e1.setTimeInMillis(searchBean.getEndDate().getTime());
		e1.set(Calendar.HOUR_OF_DAY, 0);
		e1.set(Calendar.MINUTE, 0);
		e1.set(Calendar.SECOND, 0);
		e1.set(Calendar.MILLISECOND, 0);
		searchBean.setEndDate(e1.getTime());
		
		enquiryPage = enqRepo.findAll(new Specification<Enquiry>() {
			@Override
			public Predicate toPredicate(Root<Enquiry> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();
				
				if (searchBean.getStartDate().before(searchBean.getEndDate()) || searchBean.getStartDate().equals(searchBean.getEndDate())) {
					p = cb.and(p, cb.between(root.get("addedDate"), searchBean.getStartDate(), searchBean.getEndDate()));
				}
				
				if (searchBean.getEnquirySourceId() > 0) {
					p = cb.and(p, cb.equal(root.join("enquirySource").get("enquirySourceId"),
							searchBean.getEnquirySourceId()));
				}

				if (searchBean.getEnquiryTypeId() > 0) {
					p = cb.and(p, cb.equal(root.join("enquiryType").get("enquiryTypeId"), searchBean.getEnquiryTypeId()));
				}

				if (searchBean.getStatus() != null && !searchBean.getStatus().equals("ALL")) {
					p = cb.and(p, cb.equal(root.get("status"), searchBean.getStatus()));
				}

				if (searchBean.getCustomerId() > 0) {
					p = cb.and(p, cb.equal(root.join("customer").get("customerId"), searchBean.getCustomerId()));
				}

				if (searchBean.getAddedBy() > 0) {
					p = cb.and(p, cb.equal(root.join("addedBy").get("userId"), searchBean.getAddedBy()));
				}

				if (user.getRole().getRolename().equals(ApplicationConstants.RoleConstants.ROLE_ADMIN)) {
					// all Enquiries should be visible
				} else if (user.getRole().getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)) {
					
					// all Enquiries should be visible that to for same Product Catagory
					UserProductCatagoryLink upcl = userProductCatagoryLinkRepo.findByUserId(user.getUserId());
					
					if (upcl.getUserProductCatagoryLinkId() > 0) {
						List<ProductType> productTypeList = productTypeRepo
								.findAllByProductCatagory(upcl.getProductCatagory().getProductCatagoryId());

						for (int i = 0; i < productTypeList.size(); i++) {
							p = cb.and(p, cb.equal(root.join("product").join("productType").join("productCatagory").get("productCatagoryId"), productTypeList.get(i).getProductCatagory().getProductCatagoryId()));
						}

					}
				} else if (user.getRole().getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_ENGINEER)) {
					p = cb.and(p, cb.equal(root.join("assignedTo").get("userId"), user.getUserId()));
				}
				
				cq
				  .where(p)
				  .orderBy(cb.desc(root.get("recentActivityDate")));
				
				return p;
			}
		}, pagable);

		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("EnquiryList", enquiryPage.getContent());
		responseMap.put("CurrentPage", enquiryPage.getNumber() + 1);
		responseMap.put("TotalItems", enquiryPage.getTotalElements());
		responseMap.put("TotalPages", enquiryPage.getTotalPages());

		return responseMap;
	}

	@Override
	public Map<String, Object> getAssignedToList() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		User user = userService.getCurrentLoggedInUser();

		if (user != null && user.getUserId() > 0) {
			List<User> assignedToList = null;
			if (user.getRole().getRolename().equals(ApplicationConstants.RoleConstants.ROLE_ADMIN)) {
				Department department = null;
				department = deptRepo.findByDepartmentName(ApplicationConstants.DepartmentConstants.SALES_DEPARMENT);
				assignedToList = userRepo.findByDepartment(department);
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseMap.put("AssignToList", assignedToList);
			} else if (user.getRole().getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)) {
				assignedToList = userRepo.findByManager(user);
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseMap.put("AssignToList", assignedToList);
			}
		} else {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
			responseMap.put("message", "User is not available.");
			responseMap.put("errorCode", "WSEM0010");
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getEnquiryDetails(Long enquiryId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Enquiry enquiry = enqRepo.findById(enquiryId).orElse(null);

		if (enquiry != null && enquiry.getEnquiryId() > 0) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("Enquiry", enquiry);
			responseMap.put("message", "");

			List<EnquiryActivity> enquiryActivities = null;
			enquiryActivities = enqActivityRepo.findByEnquiry(enquiry);
			if (enquiryActivities != null && enquiryActivities.size() > 0) {
				responseMap.put("EnquiryActivities", enquiryActivities);
			}
		} else {
			responseMap.put("message", "No enquiry found for this id!");
		}
		return responseMap;
	}
	
	@Transactional
	@Override
	public Map<String, Object> assignEnquiry(AssignEnquiryBean assignEnquiry) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Enquiry enquiry = enqRepo.findById(assignEnquiry.getEnquiryId()).orElse(null);
		User assignedTo = userRepo.findById(assignEnquiry.getAssignToId()).orElse(null);
		
		//User assignedBy = userRepo.findById(assignEnquiry.getAssignById()).orElse(null); TODO Old Code
		 User assignedBy = userService.getCurrentLoggedInUser();
		
		//User sysUser = userRepo.findById(assignEnquiry.getSysUserId()).orElse(null); TODO Old Code.

		if (enquiry != null 
				&& enquiry.getEnquiryId() > 0 
				&& enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_UNASSIGNED) 
				&& assignedTo != null && assignedTo.getUserId() > 0
				&& assignedBy != null && assignedBy.getUserId() > 0) {

			enquiry.setAssignedTo(assignedTo);
			enquiry.setAssignedBy(assignedBy);
			enquiry.setRecentActivityDate(DateUtils.getCurrentTimestamp());
			enquiry.setStatus(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_ASSIGNED);

			enquiry = enqRepo.save(enquiry);

			if (enquiry != null) {
				EnquiryActivity enqActivity = new EnquiryActivity();
				enqActivity.setEnquiry(enquiry);
				//enqActivity.setUser(sysUser);
				enqActivity.setUser(assignedBy);
				enqActivity.setStatus(enquiry.getStatus());
				enqActivity.setRecordDate(DateUtils.getCurrentTimestamp());
				enqActivity.setRemark("Enquiry Assigned to " + assignedTo.getFullName() + ", by " + assignedBy.getFullName() + " with remark " + assignEnquiry.getRemark());
				enqActivity = enqActivityRepo.save(enqActivity);
				if (enqActivity.getEnquiryActivityId() > 0) {
					responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
					responseMap.put("message", "Enquiry Assigned Successfully");
				}
				responseMap.put("enquiryId", enquiry.getEnquiryId());
			}
		} else {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
			responseMap.put("message", "Unable to Assign Enquiry! Try Again");
		}
		return responseMap;
	}
	
	@Transactional
	@Override
	public Map<String, Object> cancelEnquiry(CancelEnquiryBean cancelEnquiry) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Enquiry enquiry = enqRepo.findById(cancelEnquiry.getEnquiryId()).orElse(null);
		//User sysUser = userRepo.findById(cancelEnquiry.getSysUserId()).orElse(null); //Old Code
		User currentUser = userService.getCurrentLoggedInUser();

		if (enquiry != null && enquiry.getEnquiryId() > 0 
				&& (enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_UNASSIGNED) || enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_ASSIGNED)) 
				&& currentUser.getUserId() > 0) {

			enquiry.setRecentActivityDate(DateUtils.getCurrentTimestamp());
			enquiry.setStatus(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_CANCELLED);

			enquiry = enqRepo.save(enquiry);

			if (enquiry != null) {
				EnquiryActivity enqActivity = new EnquiryActivity();
				enqActivity.setEnquiry(enquiry);
				//enqActivity.setUser(sysUser);
				enqActivity.setUser(currentUser);
				enqActivity.setStatus(enquiry.getStatus());
				enqActivity.setRecordDate(DateUtils.getCurrentTimestamp());
				enqActivity.setRemark("Enquiry cancelled by " + currentUser.getFullName() + " with remark " + cancelEnquiry.getRemark());
				enqActivity = enqActivityRepo.save(enqActivity);
				if (enqActivity.getEnquiryActivityId() > 0) {
					responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
					responseMap.put("message", "Enquiry Cancelled Successfully");
				}
				responseMap.put("enquiryId", enquiry.getEnquiryId());
			}
		} else {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
			responseMap.put("message", "Unable to Cancel Enquiry! Try Again");
		}
		return responseMap;
	}

	@Transactional
	@Override
	public Map<String, Object> convertToProspectEnquiry(ConvertToProspectBean convertToProspect) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Enquiry enquiry = enqRepo.findById(convertToProspect.getEnquiryId()).orElse(null);
		//User sysUser = userRepo.findById(convertToProspect.getSysUserId()).orElse(null);
		User currentUser = userService.getCurrentLoggedInUser();

		if (enquiry != null && enquiry.getEnquiryId() > 0 
				&& enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_ASSIGNED) 
				&& currentUser.getUserId() > 0) {

			enquiry.setRecentActivityDate(DateUtils.getCurrentTimestamp());
			enquiry.setStatus(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_PROSPECT);

			enquiry = enqRepo.save(enquiry);

			if (enquiry != null) {
				EnquiryActivity enqActivity = new EnquiryActivity();
				enqActivity.setEnquiry(enquiry);
				//enqActivity.setUser(sysUser);
				enqActivity.setUser(currentUser);
				enqActivity.setStatus(enquiry.getStatus());
				enqActivity.setRecordDate(DateUtils.getCurrentTimestamp());
				enqActivity.setRemark("Enquiry converted to Prospect by " + currentUser.getFullName() + " with remark " + convertToProspect.getRemark());
				enqActivity = enqActivityRepo.save(enqActivity);
				if (enqActivity.getEnquiryActivityId() > 0) {
					responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
					responseMap.put("message", "Enquiry Converted to Prospect Successfully");
				}
				responseMap.put("enquiryId", enquiry.getEnquiryId());
			}
		} else {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
			responseMap.put("message", "Unable to update Enquiry! Try Again");
		}
		return responseMap;
	}

	@Transactional
	@Override
	public Map<String, Object> wonEnquiry(WonLostEnquiryBean wonEnquiry) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Enquiry enquiry = enqRepo.findById(wonEnquiry.getEnquiryId()).orElse(null);
		//User sysUser = userRepo.findById(wonEnquiry.getSysUserId()).orElse(null);
		User currentUser = userService.getCurrentLoggedInUser();

		if (enquiry != null && enquiry.getEnquiryId() > 0 
				&& enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_PROSPECT) 
				&& currentUser.getUserId() > 0) {

			enquiry.setRecentActivityDate(DateUtils.getCurrentTimestamp());
			enquiry.setStatus(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_WON);

			enquiry = enqRepo.save(enquiry);

			if (enquiry != null) {
				EnquiryActivity enqActivity = new EnquiryActivity();
				enqActivity.setEnquiry(enquiry);
				//enqActivity.setUser(sysUser);
				enqActivity.setUser(currentUser);
				enqActivity.setStatus(enquiry.getStatus());
				enqActivity.setRecordDate(DateUtils.getCurrentTimestamp());
				enqActivity.setRemark("Enquiry Won!  by " + currentUser.getFullName() + " with remark " + wonEnquiry.getRemark());
				enqActivity = enqActivityRepo.save(enqActivity);
				if (enqActivity.getEnquiryActivityId() > 0) {
					responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
					responseMap.put("message", "Enquiry won Successfully");
					
					Customer customer = enquiry.getCustomer();
					if(customer != null && customer.getCustomerId() > 0) {
						if(customer.getCustomerType().equals(ApplicationConstants.CustomerConstants.CUSTOMER_TYPE_PROSPECT)) {
							customer.setCustomerType(ApplicationConstants.CustomerConstants.CUSTOMER_TYPE_CUSTOMER);
							customer = customerRepo.save(customer);
						}
					}
					
					//add Product Customer Link here
					
					CustomerProductLink custProductLink = new CustomerProductLink();
					custProductLink.setCustomer(customer);
					custProductLink.setProductCatagory(enquiry.getProduct().getProductType().getProductCatagory());
					custProductLink.setProductType(enquiry.getProduct().getProductType());
					custProductLink.setBrand(enquiry.getProduct().getBrand());
					custProductLink.setProduct(enquiry.getProduct());
					
					
					Calendar c1=Calendar.getInstance();
					Timestamp purchasedDate =new Timestamp(System.currentTimeMillis());
			    	 c1.setTimeInMillis(wonEnquiry.getPurchaseDate().getTime());
			    	 c1.set(Calendar.HOUR_OF_DAY, 0);
			    	 c1.set(Calendar.MINUTE, 0);
			    	 c1.set(Calendar.SECOND, 0);
			    	 c1.set(Calendar.MILLISECOND, 0);
			    	 purchasedDate.setTime(c1.getTimeInMillis());
					custProductLink.setDateOfPurchase(purchasedDate);
					 
					//warranty till
					Calendar c=Calendar.getInstance();
					 Timestamp ts = DateUtils.getCurrentTimestamp();
			    	 c.setTimeInMillis(ts.getTime());
			    	 c.set(Calendar.HOUR_OF_DAY, 0);
			    	 c.set(Calendar.MINUTE, 0);
			    	 c.set(Calendar.SECOND, 0);
			    	 c.set(Calendar.MILLISECOND, 0);
			    	 c.add(Calendar.YEAR, enquiry.getProduct().getWarrantyInYears().intValue());
			    	 ts.setTime(c.getTimeInMillis());
					custProductLink.setWarrantyTill(ts);
					custProductLink.setQuantity(1L);
					custProductLink.setMachineSerialNumber(wonEnquiry.getMachineSerialNumber());
					custProductLink.setRecordDate(DateUtils.getCurrentTimestamp());
					custProductLink.setRemark("Customer Purchased this Product");
					custProductLinkRepo.save(custProductLink);
				}
				responseMap.put("enquiryId", enquiry.getEnquiryId());
			}
			
		} else {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
			responseMap.put("message", "Unable to update Enquiry! Try Again");
		}
		return responseMap;
	}

	@Transactional
	@Override
	public Map<String, Object> lostEnquiry(WonLostEnquiryBean lostEnquiry) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Enquiry enquiry = enqRepo.findById(lostEnquiry.getEnquiryId()).orElse(null);
		//User sysUser = userRepo.findById(lostEnquiry.getSysUserId()).orElse(null);
		User currentUser = userService.getCurrentLoggedInUser();

		if (enquiry != null && enquiry.getEnquiryId() > 0 
				&& enquiry.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_PROSPECT) 
				&& currentUser.getUserId() > 0) {

			enquiry.setRecentActivityDate(DateUtils.getCurrentTimestamp());
			enquiry.setStatus(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_LOST);

			enquiry = enqRepo.save(enquiry);

			if (enquiry != null) {
				EnquiryActivity enqActivity = new EnquiryActivity();
				enqActivity.setEnquiry(enquiry);
				enqActivity.setUser(currentUser);
				enqActivity.setStatus(enquiry.getStatus());
				enqActivity.setRecordDate(DateUtils.getCurrentTimestamp());
				enqActivity.setRemark("Enquiry Lost!  by " + currentUser.getFullName() + " with remark " + lostEnquiry.getRemark());
				enqActivity = enqActivityRepo.save(enqActivity);
				if (enqActivity.getEnquiryActivityId() > 0) {
					responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
					responseMap.put("message", "Enquiry marked as Lost Successfully");
				}
				responseMap.put("enquiryId", enquiry.getEnquiryId());
			}
		} else {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
			responseMap.put("message", "Unable to update Enquiry! Try Again");
		}
		return responseMap;
	}

}
