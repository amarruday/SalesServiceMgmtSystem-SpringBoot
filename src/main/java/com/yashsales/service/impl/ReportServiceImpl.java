package com.yashsales.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.dto.commons.DatewiseSearchBean;
import com.yashsales.entity.Enquiry;
import com.yashsales.entity.EnquirySource;
import com.yashsales.entity.Product;
import com.yashsales.entity.Ticket;
import com.yashsales.entity.TicketType;
import com.yashsales.entity.User;
import com.yashsales.repository.EnquiryRepository;
import com.yashsales.repository.TicketRepository;
import com.yashsales.service.ReportService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

	private final TicketRepository ticketRepo;
	private final EnquiryRepository enqRepo;

	@Override
	public Map<String, Object> getTicketTypeWiseTicketReport(DatewiseSearchBean searchBean) {
		// check if dates are empty the apply start of month and end of the month
		System.out.println(searchBean.getStartDate());
		System.out.println(searchBean.getEndDate());
		
		LocalDate currentDate = LocalDate.now();
		if (searchBean.getStartDate() == null) {
			LocalDate firstDay = currentDate.with(TemporalAdjusters.firstDayOfMonth());
			searchBean.setStartDate(Date.valueOf(firstDay));
		}

		if (searchBean.getEndDate() == null) {
			searchBean.setEndDate(Date.valueOf(currentDate));
		}

		List<Ticket> ticketList = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<TicketType, Map<String, Long>> ticketTypeWiseTicketList = new HashMap<TicketType, Map<String, Long>>();

		// ticketList = ticketRepo.findAll();
		ticketList = ticketRepo.findAll(new Specification<Ticket>() {
			@Override
			public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();

				if (searchBean.getStartDate().before(searchBean.getEndDate())	|| searchBean.getStartDate().equals(searchBean.getEndDate())) {
					p = cb.and(p,
							cb.between(root.get("addedDate"), searchBean.getStartDate(), searchBean.getEndDate()));
				}

				return p;
			}
		});

		ticketTypeWiseTicketList = ticketList.stream().collect(Collectors.groupingBy(Ticket::getTicketType,	Collectors.groupingBy(Ticket::getStatus, Collectors.counting())));
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		returnMap.put("ticketTypeWiseTicketList", ticketTypeWiseTicketList);
		return returnMap;
	}

	@Override
	public Map<String, Object> getProductWiseTicketReport(DatewiseSearchBean searchBean) {

		LocalDate currentDate = LocalDate.now();
		if (searchBean.getStartDate() == null) {
			LocalDate firstDay = currentDate.with(TemporalAdjusters.firstDayOfMonth());
			searchBean.setStartDate(Date.valueOf(firstDay));
		}

		if (searchBean.getEndDate() == null) {
			searchBean.setEndDate(Date.valueOf(currentDate));
		}

		List<Ticket> ticketList = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<Product, List<Ticket>> productWiseTicketList = null;
		Map<String, List<Ticket>> reportList = new HashMap<String, List<Ticket>>();

		// ticketList = ticketRepo.findAll();
		ticketList = ticketRepo.findAll(new Specification<Ticket>() {
			@Override
			public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();

				if (searchBean.getStartDate().before(searchBean.getEndDate())
						|| searchBean.getStartDate().equals(searchBean.getEndDate())) {
					p = cb.and(p,
							cb.between(root.get("addedDate"), searchBean.getStartDate(), searchBean.getEndDate()));
				}

				return p;
			}
		});

		productWiseTicketList = ticketList.stream().collect(Collectors.groupingBy(Ticket::getProduct, Collectors.toList()));
		for (Entry<Product, List<Ticket>> entry : productWiseTicketList.entrySet()) {
			String productName = entry.getKey().getProductName();
			List<Ticket> ticketsList = entry.getValue();
			reportList.put(productName, ticketsList);
		}

		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		returnMap.put("productWiseTicketList", reportList);
		return returnMap;
	}

	@Override
	public Map<String, Object> getEmployeewiseTicketReport(DatewiseSearchBean searchBean) {
		List<Ticket> ticketList = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<User, List<Ticket>> employeeWiseTicketList = null;
		Map<String, List<Ticket>> reportList = new HashMap<String, List<Ticket>>();
		
		LocalDate currentDate = LocalDate.now();
		if (searchBean.getStartDate() == null) {
			LocalDate firstDay = currentDate.with(TemporalAdjusters.firstDayOfMonth());
			searchBean.setStartDate(Date.valueOf(firstDay));
		}

		if (searchBean.getEndDate() == null) {
			searchBean.setEndDate(Date.valueOf(currentDate));
		}
		
		// ticketList = ticketRepo.findAll();
		ticketList = ticketRepo.findAll(new Specification<Ticket>() {
			@Override
			public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();

				if (searchBean.getStartDate().before(searchBean.getEndDate()) || searchBean.getStartDate().equals(searchBean.getEndDate())) {
					p = cb.and(p, cb.between(root.get("addedDate"), searchBean.getStartDate(), searchBean.getEndDate()));
				}

				return p;
			}
		});
		
		employeeWiseTicketList = ticketList.stream()
				.collect(Collectors.groupingBy(Ticket::getAddedBy, Collectors.toList()));
		
		for (Map.Entry<User, List<Ticket>> entry : employeeWiseTicketList.entrySet()) {
			String empName = entry.getKey().getFullName() + " [" + entry.getKey().getRole().getRolename() + "]";
			List<Ticket> ticketsList = entry.getValue();
			reportList.put(empName, ticketsList);
		}

		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		returnMap.put("employeeWiseTicketList", reportList);
		return returnMap;
	}
	
	@Override
	public Map<String, Object> getProductWiseTicketTypeCountReport(DatewiseSearchBean searchBean) {
	
		
		LocalDate currentDate = LocalDate.now();
		if (searchBean.getStartDate() == null) {
			LocalDate firstDay = currentDate.with(TemporalAdjusters.firstDayOfMonth());
			searchBean.setStartDate(Date.valueOf(firstDay));
		}

		if (searchBean.getEndDate() == null) {
			searchBean.setEndDate(Date.valueOf(currentDate));
		}

		List<Ticket> ticketList = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<Product, Map<TicketType, Long>> productWiseTicketTypeCountList = new HashMap<Product, Map<TicketType, Long>>();
		Map<String, Map<String, Long>> reportList = new HashMap<String, Map<String, Long>>();
		Map<String, Map<String, Long>> finalReport = new HashMap<String, Map<String, Long>>();
		
		ticketList = ticketRepo.findAll(new Specification<Ticket>() {
			@Override
			public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();

				if (searchBean.getStartDate().before(searchBean.getEndDate()) || searchBean.getStartDate().equals(searchBean.getEndDate())) {
					p = cb.and(p, cb.between(root.get("addedDate"), searchBean.getStartDate(), searchBean.getEndDate()));
				}

				return p;
			}
		});
		
		productWiseTicketTypeCountList = ticketList.stream().collect(Collectors.groupingBy(Ticket::getProduct,	Collectors.groupingBy(Ticket::getTicketType, Collectors.counting())));
		for (Entry<Product, Map<TicketType, Long>> entry : productWiseTicketTypeCountList.entrySet()) {
			String productName = entry.getKey().getProductName();
			Map<TicketType, Long> ticketsListMap = entry.getValue();
			Map<String, Long> ticketTypeCountMap = new HashMap<String, Long>();
			for (Entry<TicketType, Long> entry1 : ticketsListMap.entrySet()) {
				String ticketTypeName = entry1.getKey().getTicketType();
				Long count = entry1.getValue();
				ticketTypeCountMap.put(ticketTypeName, count);
			}
			
			reportList.put(productName, ticketTypeCountMap);
		}
		
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		returnMap.put("productWiseTicketTypeCountList", reportList);
		return returnMap;
	}
	
	
	// Enquiry Reports

	@Override
	public Map<String, Object> getEnquirySourceEfficacyWiseEnquiryReport(DatewiseSearchBean searchBean) {
		List<Enquiry> enquiryList = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<EnquirySource, Long> enquirySourceWiseEnquiryList = new HashMap<EnquirySource, Long>();
		
		LocalDate currentDate = LocalDate.now();
		if (searchBean.getStartDate() == null) {
			LocalDate firstDay = currentDate.with(TemporalAdjusters.firstDayOfMonth());
			searchBean.setStartDate(Date.valueOf(firstDay));
		}

		if (searchBean.getEndDate() == null) {
			searchBean.setEndDate(Date.valueOf(currentDate));
		}
		
		//enquiryList = enqRepo.findAll();
		enquiryList = enqRepo.findAll(new Specification<Enquiry>() {
			@Override
			public Predicate toPredicate(Root<Enquiry> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();

				if (searchBean.getStartDate().before(searchBean.getEndDate())	|| searchBean.getStartDate().equals(searchBean.getEndDate())) {
					p = cb.and(p,	cb.between(root.get("addedDate"), searchBean.getStartDate(), searchBean.getEndDate()));
				}

				return p;
			}
		});
		
		enquirySourceWiseEnquiryList = enquiryList.stream().collect(Collectors.groupingBy(Enquiry::getEnquirySource, Collectors.counting()));
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		returnMap.put("enquirySourceEfficacyWiseEnquiryList", enquirySourceWiseEnquiryList);
		return returnMap;
	}

	@Override
	public Map<String, Object> getProductwiseEnquiryReport(DatewiseSearchBean searchBean) {
		List<Enquiry> enquiryList = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<Product, List<Enquiry>> productWiseEnquiryList = null;
		Map<String, List<Enquiry>> reportList = new HashMap<String, List<Enquiry>>();

		LocalDate currentDate = LocalDate.now();
		if (searchBean.getStartDate() == null) {
			LocalDate firstDay = currentDate.with(TemporalAdjusters.firstDayOfMonth());
			searchBean.setStartDate(Date.valueOf(firstDay));
		}

		if (searchBean.getEndDate() == null) {
			searchBean.setEndDate(Date.valueOf(currentDate));
		}
		
		//enquiryList = enqRepo.findAll();
		
		enquiryList = enqRepo.findAll(new Specification<Enquiry>() {

			@Override
			public Predicate toPredicate(Root<Enquiry> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();

				if (searchBean.getStartDate().before(searchBean.getEndDate())	|| searchBean.getStartDate().equals(searchBean.getEndDate())) {
					p = cb.and(p,	cb.between(root.get("addedDate"), searchBean.getStartDate(), searchBean.getEndDate()));
				}

				return p;
			}
		});
		 
		
		productWiseEnquiryList = enquiryList.stream().collect(Collectors.groupingBy(Enquiry::getProduct, Collectors.toList()));
		
		for (Entry<Product, List<Enquiry>> entry : productWiseEnquiryList.entrySet()) {
			String productName = entry.getKey().getProductName();
			List<Enquiry> enqList = entry.getValue();
			reportList.put(productName, enqList);
		}
		
		returnMap.put("productWiseEnquiryList", reportList);
		return returnMap;
	}
	
	@Override
	public Map<String, Object> getEmployeeWiseEnquiryReport(DatewiseSearchBean searchBean) {
		List<Enquiry> enquiryList = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<User, List<Enquiry>> employeeWiseEnquiryList = null;
		Map<String, List<Enquiry>> reportList = new HashMap<String, List<Enquiry>>();

		LocalDate currentDate = LocalDate.now();
		if (searchBean.getStartDate() == null) {
			LocalDate firstDay = currentDate.with(TemporalAdjusters.firstDayOfMonth());
			searchBean.setStartDate(Date.valueOf(firstDay));
		}

		if (searchBean.getEndDate() == null) {
			searchBean.setEndDate(Date.valueOf(currentDate));
		}
		
		//enquiryList = enqRepo.findAll();
		enquiryList = enqRepo.findAll(new Specification<Enquiry>() {
			@Override
			public Predicate toPredicate(Root<Enquiry> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();

				if (searchBean.getStartDate().before(searchBean.getEndDate())	|| searchBean.getStartDate().equals(searchBean.getEndDate())) {
					p = cb.and(p,	cb.between(root.get("addedDate"), searchBean.getStartDate(), searchBean.getEndDate()));
				}

				return p;
			}
		});
		
		employeeWiseEnquiryList = enquiryList.stream()
				.collect(Collectors.groupingBy(Enquiry::getAddedBy, Collectors.toList()));
		for (Map.Entry<User, List<Enquiry>> entry : employeeWiseEnquiryList.entrySet()) {
			String empName = entry.getKey().getFullName() + " [" + entry.getKey().getRole().getRolename() + "]";
			List<Enquiry> enqList = entry.getValue();
			reportList.put(empName, enqList);
		}
		returnMap.put("employeeWiseEnquiryList", reportList);
		return returnMap;
	}

	
	// Charts

	@Override
	public Map<String, Object> getProductTypeWiseEnquiryChartReport() {
		List<Enquiry> enqList = null;
		LocalDate now = LocalDate.now();
		Map<String, Object> returnMap = new HashMap<String, Object>();

		LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());

		Date firstDayOfMonth = Date.valueOf(firstDay);
		Date lastDayOfMonth = Date.valueOf(lastDay);

		enqList = enqRepo.findAll(new Specification<Enquiry>() {
			@Override
			public Predicate toPredicate(Root<Enquiry> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();

				if (firstDayOfMonth.before(lastDayOfMonth) || firstDayOfMonth.equals(lastDayOfMonth)) {
					p = cb.and(p, cb.between(root.get("addedDate"), firstDayOfMonth, lastDayOfMonth));
				}

				return p;
			}
		});

		Map<String, Long> productTypeCountMap = enqList.stream().collect(
				Collectors.groupingBy(i -> i.getProduct().getProductType().getProductName(), Collectors.counting()));

		List<String> productTypes = new ArrayList<String>();
		List<Long> totalEnq = new ArrayList<Long>();
		for (Map.Entry<String, Long> entry : productTypeCountMap.entrySet()) {

			productTypes.add(entry.getKey());
			totalEnq.add(entry.getValue());
		}

		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		returnMap.put("productTypes", productTypes);
		returnMap.put("totalEnq", totalEnq);

		return returnMap;
	}

	@Override
	public Map<String, Object> getProductTypeWiseTicketChartReport() {
		List<Ticket> ticketList = null;
		LocalDate now = LocalDate.now();
		Map<String, Object> returnMap = new HashMap<String, Object>();

		LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());

		Date firstDayOfMonth = Date.valueOf(firstDay);
		Date lastDayOfMonth = Date.valueOf(lastDay);

		ticketList = ticketRepo.findAll();
		
		  ticketList = ticketRepo.findAll(new Specification<Ticket>() {
		  
		  @Override public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?>
		  cq, CriteriaBuilder cb) { Predicate p = cb.conjunction();
		  
		  if (firstDayOfMonth.before(lastDayOfMonth) ||
		  firstDayOfMonth.equals(lastDayOfMonth)) { p = cb.and(p,
		  cb.between(root.get("addedDate"), firstDayOfMonth, lastDayOfMonth)); }
		  
		  return p; } });
		 
		Map<String, Long> productTypeCountMap = ticketList.stream().collect(
				Collectors.groupingBy(i -> i.getProduct().getProductType().getProductName(), Collectors.counting()));

		List<String> productTypes = new ArrayList<String>();
		List<Long> totalTicks = new ArrayList<Long>();
		for (Map.Entry<String, Long> entry : productTypeCountMap.entrySet()) {

			productTypes.add(entry.getKey());
			totalTicks.add(entry.getValue());
		}

		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		returnMap.put("productTypes", productTypes);
		returnMap.put("totalTicks", totalTicks);

		return returnMap;
	}

	@Override
	public Map<String, Object> getTicketTypeWiseTicketChartReport() {
		List<Ticket> ticketList = null;
		LocalDate now = LocalDate.now();
		Map<String, Object> returnMap = new HashMap<String, Object>();

		LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());

		Date firstDayOfMonth = Date.valueOf(firstDay);
		Date lastDayOfMonth = Date.valueOf(lastDay);

		ticketList = ticketRepo.findAll();
		
		
		  ticketList = ticketRepo.findAll(new Specification<Ticket>() {
		  
		  @Override public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?>
		  cq, CriteriaBuilder cb) { Predicate p = cb.conjunction();
		  
		  if (firstDayOfMonth.before(lastDayOfMonth) ||
		  firstDayOfMonth.equals(lastDayOfMonth)) { p = cb.and(p,
		  cb.between(root.get("addedDate"), firstDayOfMonth, lastDayOfMonth)); }
		  
		  return p; } });
		 
		Map<String, Long> productTypeCountMap = ticketList.stream()
				.collect(Collectors.groupingBy(i -> i.getTicketType().getTicketType(), Collectors.counting()));

		List<String> ticketTypes = new ArrayList<String>();
		List<Long> totalTicks = new ArrayList<Long>();
		for (Map.Entry<String, Long> entry : productTypeCountMap.entrySet()) {

			ticketTypes.add(entry.getKey());
			totalTicks.add(entry.getValue());
		}

		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		returnMap.put("ticketTypes", ticketTypes);
		returnMap.put("totalTicks", totalTicks);

		return returnMap;
	}

}
