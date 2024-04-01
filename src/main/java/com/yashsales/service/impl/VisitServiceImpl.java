package com.yashsales.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.dto.SearchVisitBean;
import com.yashsales.dto.VisitBean;
import com.yashsales.entity.ActionType;
import com.yashsales.entity.Customer;
import com.yashsales.entity.User;
import com.yashsales.entity.Visit;
import com.yashsales.repository.ActionTypeRepository;
import com.yashsales.repository.CustomerRepository;
import com.yashsales.repository.VisitsRepository;
import com.yashsales.service.UserService;
import com.yashsales.service.VisitService;
import com.yashsales.utility.DateUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VisitServiceImpl implements VisitService {

	private VisitsRepository visitRepo;
	private CustomerRepository custRepo;
	private UserService userService;
	private ActionTypeRepository actionTypeRepo;

	@Override
	@Transactional
	public Map<String, Object> addNewVisit(VisitBean visitBean) {
		Visit visit = null;
		Customer customer = null;
		ActionType actionType = null;
		Map<String, Object> responseStatus = new HashMap<String, Object>();
		responseStatus.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseStatus.put("message", "Failed to add new Visit!");
		responseStatus.put("Visit", null);
		try {
			if (visitBean != null) {
				visit = new Visit();

				if (visitBean.getCustomerId() != null && visitBean.getCustomerId() > 0) {
					customer = custRepo.findById(visitBean.getCustomerId()).get();
					visit.setCustomer(customer);
				}

				if (visitBean.getActionTypeId() != null && visitBean.getActionTypeId() > 0) {
					actionType = actionTypeRepo.findById(visitBean.getActionTypeId()).get();
					visit.setActionType(actionType);
				}

				visit.setDescription(visitBean.getDescription());
				User currentUser = userService.getCurrentLoggedInUser();
				
				visit.setAddedBy(currentUser);
				visit.setAddedDate(DateUtils.getCurrentTimestamp());
				visit = visitRepo.save(visit);
				responseStatus.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseStatus.put("message", "Visit added successfully!");
				responseStatus.put("Visit", visit);
			}
		} catch (Exception e) {
			responseStatus.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
			responseStatus.put("message", "Failed to add new Visit!");
		}
		return responseStatus;
	}

	@Override
	public Map<String, Object> getVisitDetails(Long visitId) {
		Visit visit = null;
		Map<String, Object> responseStatus = new HashMap<String, Object>();
		responseStatus.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseStatus.put("message", "Failed to fetch visit details! Please try again.");
		responseStatus.put("Visit", null);

		if (visitId > 0 && visitId != null) {
			visit = visitRepo.findById(visitId).get();

			VisitBean visitBean = new VisitBean();
			visitBean.setVisitId(visit.getVisitId());
			visitBean.setCustomer(visit.getCustomer());
			visitBean.setActionTypeName(visit.getActionType().getActionType());
			visitBean.setDescription(visit.getDescription());
			visitBean.setAddedByName(visit.getAddedBy().getFullName());
			visitBean.setAddedDate(visit.getAddedDate());

			responseStatus.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseStatus.put("message", "");
			responseStatus.put("Visit", visitBean);
		}
		return responseStatus;
	}

	@Override
	public Map<String, Object> getAllVisits() {
		List<Visit> visitList = null;
		List<VisitBean> visitBeanList = new ArrayList<VisitBean>();

		Map<String, Object> responseStatus = new HashMap<String, Object>();
		responseStatus.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseStatus.put("message", "Failed to fetch visit details! Please try again.");
		responseStatus.put("VisitList", null);

		visitList = visitRepo.findAll();
		if (!visitList.isEmpty()) {
			visitList.forEach(visit -> {
				VisitBean visitBean = new VisitBean();
				visitBean.setVisitId(visit.getVisitId());
				visitBean.setCustomer(visit.getCustomer());
				visitBean.setActionTypeName(visit.getActionType().getActionType());
				visitBean.setDescription(visit.getDescription());
				visitBean.setAddedByName(visit.getAddedBy().getFullName());
				visitBean.setAddedDate(visit.getAddedDate());
				visitBeanList.add(visitBean);
			});

			responseStatus.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseStatus.put("message", "");
			responseStatus.put("VisitList", visitBeanList);
		}
		return responseStatus;
	}

	@Override
	public Map<String, Object> searchVisits(SearchVisitBean searchVisitBean) {
		List<Visit> visitsList = null;
		List<VisitBean> visitBeanList = new ArrayList<VisitBean>();

		Map<String, Object> responseStatus = new HashMap<String, Object>();
		responseStatus.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseStatus.put("message", "Failed to fetch visit details! Please try again.");
		responseStatus.put("VisitList", null);

		LocalDate currentDate = LocalDate.now();
		if (searchVisitBean.getStartDate() == null) {
			LocalDate startDate = currentDate.minus(1, ChronoUnit.WEEKS);
			Date date = Date.valueOf(startDate);
			searchVisitBean.setStartDate(date);
		}

		if (searchVisitBean.getEndDate() == null) {
			Date date = Date.valueOf(currentDate);
			searchVisitBean.setEndDate(date);
		}
		
		Calendar s1=Calendar.getInstance();
   	 	s1.setTimeInMillis(searchVisitBean.getStartDate().getTime());
   	 	s1.set(Calendar.HOUR_OF_DAY, 0);
   	 	s1.set(Calendar.MINUTE, 0);
   	 	s1.set(Calendar.SECOND, 0);
   	 	s1.set(Calendar.MILLISECOND, 0);
   	 	searchVisitBean.setStartDate(s1.getTime());
   	 
   	 	Calendar e1=Calendar.getInstance();
   	 	e1.setTimeInMillis(searchVisitBean.getEndDate().getTime());
   	 	e1.set(Calendar.HOUR_OF_DAY, 0);
   	 	e1.set(Calendar.MINUTE, 0);
   	 	e1.set(Calendar.SECOND, 0);
   	 	e1.set(Calendar.MILLISECOND, 0);
   	 	searchVisitBean.setEndDate(e1.getTime());
		
		if(!searchVisitBean.getEndDate().before(searchVisitBean.getStartDate())) {
			visitsList = visitRepo.findAll(new Specification<Visit>() {
				@Override
				public Predicate toPredicate(Root<Visit> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
					Predicate p = cb.conjunction();
					
					if(searchVisitBean.getStartDate().before(searchVisitBean.getEndDate()) || searchVisitBean.getStartDate().equals(searchVisitBean.getEndDate())) {
						p = cb.and(p, cb.between(root.get("addedDate"), searchVisitBean.getStartDate(), searchVisitBean.getEndDate()));
					}
									
					if(searchVisitBean.getActionTypeId() > 0) {
						p = cb.and(p, cb.equal(root.join("actionType").get("actionTypeId"), searchVisitBean.getActionTypeId()));
					}
					
					if(searchVisitBean.getUserId() > 0) {
						p = cb.and(p, cb.equal(root.join("addedBy").get("userId"), searchVisitBean.getUserId()));
					}
					
					if(searchVisitBean.getCustomerId() > 0) {
						p = cb.and(p, cb.equal(root.join("customer").get("customerId"), searchVisitBean.getCustomerId()));
					}
					return p;
				}
			});
		}

		if (visitsList == null || visitsList.isEmpty()) {
			responseStatus.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseStatus.put("message", "");
			responseStatus.put("VisitList", null);
		}

		if (visitsList != null && visitsList.size() > 0) {
			visitsList.forEach(visit -> {
				VisitBean visitBean = new VisitBean();
				visitBean.setVisitId(visit.getVisitId());
				visitBean.setCustomer(visit.getCustomer());
				visitBean.setActionTypeName(visit.getActionType().getActionType());
				visitBean.setDescription(visit.getDescription());
				visitBean.setAddedByName(visit.getAddedBy().getFullName());
				visitBean.setAddedDate(visit.getAddedDate());
				visitBeanList.add(visitBean);
			});
			responseStatus.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseStatus.put("message", "");
			responseStatus.put("VisitList", visitBeanList);
		}
		return responseStatus;
	}

}
