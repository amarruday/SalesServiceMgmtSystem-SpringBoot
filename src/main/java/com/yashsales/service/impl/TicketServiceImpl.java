package com.yashsales.service.impl;

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

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.dto.Ticket.AddTicketBean;
import com.yashsales.dto.Ticket.TicketResponseBean;
import com.yashsales.dto.Ticket.TicketSearchBean;
import com.yashsales.entity.Customer;
import com.yashsales.entity.CustomerProductLink;
import com.yashsales.entity.Product;
import com.yashsales.entity.ProductType;
import com.yashsales.entity.Ticket;
import com.yashsales.entity.TicketActivity;
import com.yashsales.entity.TicketType;
import com.yashsales.entity.User;
import com.yashsales.entity.UserProductCatagoryLink;
import com.yashsales.repository.CustomerProudctLinkRepository;
import com.yashsales.repository.CustomerRepository;
import com.yashsales.repository.ProductRepository;
import com.yashsales.repository.ProductTypeRepository;
import com.yashsales.repository.TicketActivityRepository;
import com.yashsales.repository.TicketRepository;
import com.yashsales.repository.TicketTypeRepository;
import com.yashsales.repository.UserProductCatagoryLinkRepository;
import com.yashsales.repository.UserRepository;
import com.yashsales.service.TicketService;
import com.yashsales.service.UserService;
import com.yashsales.utility.DateUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepo;
	private final UserService userService;
	private final UserProductCatagoryLinkRepository userProductCatagoryLinkRepo;
	private final ProductTypeRepository productTypeRepo;
	private final CustomerRepository custRepo;
	private final TicketTypeRepository ticketTypeRepo;
	private final ProductRepository productRepo;
	private final UserRepository userRepo;
	private final TicketActivityRepository ticketActivityRepo;
	private final CustomerProudctLinkRepository custProdLinkRepo;
	
	@Transactional
	@Override
	public Map<String, Object> addTicket(AddTicketBean addTicketBean) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Ticket ticket = null;
		Customer customer = null;
		TicketType ticketType = null;
		Product product = null;
		User assignedTo = null;
		
		User currentUser = userService.getCurrentLoggedInUser();
		ticket = new Ticket();
		if (addTicketBean.getCustomerId() > 0) {
			customer = custRepo.findById(addTicketBean.getCustomerId()).orElse(null);
			if (customer != null && customer.getCustomerId() > 0) {
				ticket.setCustomer(customer);
			}
		}

		if (addTicketBean.getTicketTypeId() > 0) {
			ticketType = ticketTypeRepo.findById(addTicketBean.getTicketTypeId()).orElse(null);
			if (ticketType != null && ticketType.getTicketTypeId() > 0) {
				ticket.setTicketType(ticketType);
			}
		}

		if (addTicketBean.getProductId() > 0) {
			product = productRepo.findById(addTicketBean.getProductId()).orElse(null);
			if (product != null && product.getProductId() > 0) {
				ticket.setProduct(product);
			}
		}
		
		//priority
		if(addTicketBean.getPriority() != null) {
			ticket.setPriority(addTicketBean.getPriority());
		} else {
			ticket.setPriority(ApplicationConstants.TicketConstants.TICKET_PRIORITY_LOW);
		}

		// last service date
		List<Ticket> oldTickets = ticketRepo.findByMachineSerialNumber(addTicketBean.getMachineSerialNumber());
		if (oldTickets != null && oldTickets.size() > 0) {
			Ticket theTicket = oldTickets.get(0);
			ticket.setLastServiceDate(theTicket.getLastServiceDate());
		} else {
			ticket.setLastServiceDate(DateUtils.getCurrentTimestamp());
		}
		
		Calendar wd = Calendar.getInstance();
		wd.setTime(addTicketBean.getWarrantyDate());
		
		Calendar today = Calendar.getInstance();
		today.setTime(DateUtils.getCurrentTimestamp());
		
		Timestamp ts =new Timestamp(System.currentTimeMillis());
		ts.setTime(wd.getTimeInMillis());
		ticket.setWarrantyDate(ts);
		
		ticket.setInWarranty(false);
		if(wd.after(today)) {
			ticket.setInWarranty(true);
		}
		
		ticket.setMachineSerialNumber(addTicketBean.getMachineSerialNumber());
		
		// status
		ticket.setStatus(ApplicationConstants.TicketConstants.TICKET_STATUS_UNASSIGNED);

		// assignedTo
		if (addTicketBean.getAssignToId()  != null && addTicketBean.getAssignToId() > 0) {
			// assignBy
			ticket.setAssignedBy(currentUser);

			assignedTo = userRepo.findById(addTicketBean.getAssignToId()).orElse(null);
			if (assignedTo != null && assignedTo.getUserId() > 0) {
				ticket.setAssignedTo(assignedTo);
				ticket.setStatus(ApplicationConstants.TicketConstants.TICKET_STATUS_ASSIGNED);
			}
		}

		// addedBy
		ticket.setAddedBy(currentUser);
		ticket.setAddedDate(DateUtils.getCurrentTimestamp());
		// Short Description
		ticket.setShortDescription(addTicketBean.getShortDescription());

		// Long description
		ticket.setLongDescription(addTicketBean.getLongDescription());

		// recentActivityDate
		ticket.setRecentActivityDate(DateUtils.getCurrentTimestamp());

		ticket = ticketRepo.save(ticket);
		if (ticket != null && ticket.getTicketId() > 0) {
			TicketActivity ticketActivity = new TicketActivity();
			ticketActivity.setUser(currentUser);
			ticketActivity.setTicket(ticket);
			ticketActivity.setStatus(ticket.getStatus());
			ticketActivity.setRecordDate(DateUtils.getCurrentTimestamp());
			if (ticket.getStatus().equals(ApplicationConstants.TicketConstants.TICKET_STATUS_ASSIGNED)) {
				ticketActivity.setRemark("Ticket " + ticket.getProduct().getTicketPrefix() + ticket.getTicketId() + " " + ticket.getShortDescription() + " is Assigned to " + ticket.getAssignedTo().getFullName() + " by " + ticket.getAssignedBy().getFullName());
			} else if (ticket.getStatus().equals(ApplicationConstants.TicketConstants.TICKET_STATUS_UNASSIGNED)) {
				ticketActivity.setRemark("Ticket " + ticket.getProduct().getTicketPrefix() + ticket.getTicketId() + " " + ticket.getShortDescription() + " is Added as Unassigned");
			}
			
			ticketActivity = ticketActivityRepo.save(ticketActivity);

			if (ticketActivity != null && ticketActivity.getTicketActivityId() > 0) {
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseMap.put("message", "Ticket added Successfully.");
			}

		}

		return responseMap;

	}

	@Override
	public Map<String, Object> getPaginatedTickets(TicketSearchBean searchBean) {		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Page<Ticket> ticketPage = null;
		int pageNumber = Math.subtractExact(searchBean.getPageNumber(), 1);
		Pageable pagable = PageRequest.of(pageNumber, searchBean.getPageSize());

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
		
		ticketPage = ticketRepo.findAll(new Specification<Ticket>() {
			@Override
			public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();
				
				if (searchBean.getStartDate().before(searchBean.getEndDate()) || searchBean.getStartDate().equals(searchBean.getEndDate())) {
					p = cb.and(p, cb.between(root.get("addedDate"), searchBean.getStartDate(), searchBean.getEndDate()));
				}

				if (searchBean.getTicketTypeId() > 0) {
					p = cb.and(p, cb.equal(root.join("ticketType").get("ticketTypeId"), searchBean.getTicketTypeId()));
				}

				if (searchBean.getPriority() != null && !searchBean.getPriority().equalsIgnoreCase("All")) {
					p = cb.and(p, cb.equal(root.get("priority"), searchBean.getPriority()));
				}

				if (searchBean.getStatus() != null && !searchBean.getStatus().equalsIgnoreCase("All")) {
					p = cb.and(p, cb.equal(root.get("status"), searchBean.getStatus()));
				}

				if (searchBean.getCustomerId() > 0) {
					p = cb.and(p, cb.equal(root.join("customer").get("customerId"), searchBean.getCustomerId()));
				}

				if (searchBean.getAddedBy() > 0) {
					p = cb.and(p, cb.equal(root.join("addedBy").get("userId"), searchBean.getAddedBy()));
				}

				if (user.getRole().getRolename().equals(ApplicationConstants.RoleConstants.ROLE_ADMIN)) {
					// all tickets should be visible
				} else if (user.getRole().getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)) {
					// all tickets should be visible that to for same Product Catagory
					UserProductCatagoryLink upcl = userProductCatagoryLinkRepo.findByUserId(user.getUserId());
					if (upcl.getUserProductCatagoryLinkId() > 0) {
						List<ProductType> productTypeList = productTypeRepo.findAllByProductCatagory(upcl.getProductCatagory().getProductCatagoryId());

						for (int i = 0; i < productTypeList.size(); i++) {
							p = cb.and(p, 	cb.equal(	root.join("product").join("productType").join("productCatagory").get("productCatagoryId"), productTypeList.get(i).getProductCatagory().getProductCatagoryId()));
						}

					}
				} else if (user.getRole().getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_ENGINEER)) {
					p = cb.and(p, cb.equal(root.join("assignedTo").get("userId"), user.getUserId()));
				} 

				cq.where(p).orderBy(cb.desc(root.get("recentActivityDate")));
				return p;
			}
		}, pagable);

		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("TicketList", ticketPage.getContent());
		responseMap.put("CurrentPage", ticketPage.getNumber() + 1);
		responseMap.put("TotalItems", ticketPage.getTotalElements());
		responseMap.put("TotalPages", ticketPage.getTotalPages());
		return responseMap;
	}

	@Override
	public Map<String, Object> getTicketDetails(Long ticketId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Ticket ticket = ticketRepo.findById(ticketId).orElse(null);
		TicketResponseBean ticketResponseBean = TicketResponseBean.builder()
				.ticketId(ticket.getTicketId())
				.ticketType(ticket.getTicketType())
				.customer(ticket.getCustomer())
				.product(ticket.getProduct())
				.priority(ticket.getPriority())
				.lastServiceDate(ticket.getLastServiceDate())
				.warrantyDate(ticket.getWarrantyDate())
				.isInWarranty(ticket.isInWarranty())
				.machineSerialNumber(ticket.getMachineSerialNumber())
				.shortDescription(ticket.getShortDescription())
				.longDescription(ticket.getLongDescription())
				.addedDate(ticket.getAddedDate())
				.recentActivityDate(ticket.getRecentActivityDate())
				.status(ticket.getStatus())
				.addedBy(ticket.getAddedBy())
				.assignedBy(ticket.getAssignedBy())
				.assignedTo(ticket.getAssignedTo())
				.build();
		
		if (ticketResponseBean != null && ticketResponseBean.getTicketId() > 0) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("Ticket", ticketResponseBean);
			responseMap.put("message", "");
			
			List<TicketActivity> ticketActivities = null;
			ticketActivities = ticketActivityRepo.findByTicket(ticket);
			if (ticketActivities != null && ticketActivities.size() > 0) {
				responseMap.put("TicketActivities", ticketActivities);
			}
		} else {
			responseMap.put("message", "No ticket found for this id!");
		}
		return responseMap;
	}

	@Transactional
	@Override
	public Map<String, Object> changeTicketStatus(Map<String, Object> changeStatusBean) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		returnMap.put("message", "Failed to update! Try again");
		
		String status = changeStatusBean.get("status").toString();
		Long ticketId = Long.valueOf(changeStatusBean.get("ticketId").toString());
		Ticket ticket = null;
		TicketActivity ticketActivity = null;
		
		User currentUser = userService.getCurrentLoggedInUser();
		if(ticketId > 0) {
			ticket = ticketRepo.findById(ticketId).orElse(null);
		}
		
		if (status != null && !status.equals("")) {
			
			if(status.equals(ApplicationConstants.TicketConstants.TICKET_STATUS_ASSIGNED)) {
				Long assignTo = Long.valueOf(changeStatusBean.get("assignToId").toString());
				User assignToUser = null;
				if(assignTo > 0 &&ticket != null && ticket.getTicketId() > 0) {
					assignToUser = userRepo.findById(assignTo).orElse(null);
					if(assignToUser != null && assignToUser.getUserId() > 0) {
						
						ticket.setAssignedTo(assignToUser);
						ticket.setAssignedBy(currentUser);
						ticket.setStatus(ApplicationConstants.TicketConstants.TICKET_STATUS_ASSIGNED);
						ticket.setRecentActivityDate(DateUtils.getCurrentTimestamp());
						ticketRepo.save(ticket);
						
						ticketActivity = new TicketActivity();
						ticketActivity.setUser(currentUser);
						ticketActivity.setTicket(ticket);
						ticketActivity.setStatus(ticket.getStatus());
						ticketActivity.setRemark("Ticket is Assigned to " + assignToUser.getFullName() + " by " + currentUser.getFullName() + " with Remark " + changeStatusBean.get("remark").toString());
						ticketActivity.setRecordDate(DateUtils.getCurrentTimestamp());
						ticketActivityRepo.save(ticketActivity);
						returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
						returnMap.put("message", "Ticket Assigned Successfully!");
					}
				}
			} else if(status.equals(ApplicationConstants.TicketConstants.TICKET_STATUS_INPROGRESS)) {
				if(ticket != null && ticket.getTicketId() > 0) {
					
					ticket.setStatus(ApplicationConstants.TicketConstants.TICKET_STATUS_INPROGRESS);
					ticket.setRecentActivityDate(DateUtils.getCurrentTimestamp());
					ticketRepo.save(ticket);
					
					ticketActivity = new TicketActivity();
					ticketActivity.setUser(currentUser);
					ticketActivity.setTicket(ticket);
					ticketActivity.setStatus(ticket.getStatus());
					ticketActivity.setRemark("Ticket is In Progress Now by " + currentUser.getFullName() + " with Remark " + changeStatusBean.get("remark").toString());
					ticketActivity.setRecordDate(DateUtils.getCurrentTimestamp());
					ticketActivityRepo.save(ticketActivity);
					returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
					returnMap.put("message", "Ticket is in In-Progress");
				}
			} else if(status.equals(ApplicationConstants.TicketConstants.TICKET_STATUS_COMPLETED)) {
				if(ticket != null && ticket.getTicketId() > 0) {
					ticket.setStatus(ApplicationConstants.TicketConstants.TICKET_STATUS_COMPLETED);
					ticket.setRecentActivityDate(DateUtils.getCurrentTimestamp());
					ticketRepo.save(ticket);
					
					ticketActivity = new TicketActivity();
					ticketActivity.setUser(currentUser);
					ticketActivity.setTicket(ticket);
					ticketActivity.setStatus(ticket.getStatus());
					ticketActivity.setRemark("Ticket is Completed by "  + currentUser.getFullName() + " with Remark " + changeStatusBean.get("remark").toString());
					ticketActivity.setRecordDate(DateUtils.getCurrentTimestamp());
					ticketActivityRepo.save(ticketActivity);
					returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
					returnMap.put("message", "Ticket Completed Successfully!");
				}
			} else if(status.equals(ApplicationConstants.TicketConstants.TICKET_STATUS_CANCELLED)) {
				if(ticket != null && ticket.getTicketId() > 0) {	
					ticket.setStatus(ApplicationConstants.TicketConstants.TICKET_STATUS_CANCELLED);
					ticket.setRecentActivityDate(DateUtils.getCurrentTimestamp());
					ticketRepo.save(ticket);
					
					ticketActivity = new TicketActivity();
					ticketActivity.setUser(currentUser);
					ticketActivity.setTicket(ticket);
					ticketActivity.setStatus(ticket.getStatus());
					ticketActivity.setRemark("Ticket Cancelled by " + currentUser.getFullName() + " with Remark " + changeStatusBean.get("remark").toString());
					ticketActivity.setRecordDate(DateUtils.getCurrentTimestamp());
					ticketActivityRepo.save(ticketActivity);
					returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
					returnMap.put("message", "Ticket Cancelled!");
				}
			} else if(status.equals(ApplicationConstants.TicketConstants.TICKET_STATUS_CLOSED)) {
				if(ticket != null && ticket.getTicketId() > 0) {	
					ticket.setStatus(ApplicationConstants.TicketConstants.TICKET_STATUS_CLOSED);
					ticket.setRecentActivityDate(DateUtils.getCurrentTimestamp());
					ticketRepo.save(ticket);
					
					ticketActivity = new TicketActivity();
					ticketActivity.setUser(currentUser);
					ticketActivity.setTicket(ticket);
					ticketActivity.setStatus(ticket.getStatus());
					ticketActivity.setRemark("Ticket Closed by " + currentUser.getFullName() + " with Remark " + changeStatusBean.get("remark").toString());
					ticketActivity.setRecordDate(DateUtils.getCurrentTimestamp());
					ticketActivityRepo.save(ticketActivity);
					returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
					returnMap.put("message", "Ticket Closed!");
				}
			}
			returnMap.put("ticketId", ticket.getTicketId());
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getProductListByCustomer(Long customerId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		Customer customer = null;
		customer = custRepo.findById(customerId).orElse(null);
			
		if(customer != null && customer.getCustomerId() > 0) {
			List<CustomerProductLink> customerProductLinkList = null;
			customerProductLinkList = custProdLinkRepo.findByCustomer(customer);
			if(customerProductLinkList != null && customerProductLinkList.size() > 0) {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				returnMap.put("CustomerProductLinkList", customerProductLinkList);
			}
		}
		return returnMap;
	}

}

