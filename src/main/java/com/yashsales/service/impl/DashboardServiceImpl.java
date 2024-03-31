package com.yashsales.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.entity.Enquiry;
import com.yashsales.entity.Ticket;
import com.yashsales.entity.User;
import com.yashsales.repository.EnquiryRepository;
import com.yashsales.repository.TicketRepository;
import com.yashsales.service.DashboardService;
import com.yashsales.service.UserService;
import com.yashsales.utility.DateUtils;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired	private UserService userService;
	
	@Autowired private TicketRepository ticketRepo;
	
	@Autowired private EnquiryRepository enqRepo;
	
	@Override
	public Map<String, Object> getDashboardDetails() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		User currentUser = userService.getCurrentLoggedInUser();
		if(currentUser != null && currentUser.getUserId() > 0) {
			
			if(currentUser.getRole().getRolename().equals(ApplicationConstants.RoleConstants.ROLE_ADMIN)) {
				
				//Ticket Statistics - All count status for today
				List<Ticket> allTickets = null;
				allTickets = ticketRepo.findAll();
				
				Calendar c=Calendar.getInstance();
				Timestamp startOfMonth = DateUtils.getCurrentTimestamp();
				c.setTimeInMillis(startOfMonth.getTime());
				
				c.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
				c.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
				c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
				c.set(Calendar.HOUR_OF_DAY, 0);
		    	c.set(Calendar.MINUTE, 0);
		    	c.set(Calendar.SECOND, 0);
		    	startOfMonth.setTime(c.getTimeInMillis());
		    	
				Timestamp endOfMonth = DateUtils.getCurrentTimestamp();
				c.setTimeInMillis(endOfMonth.getTime());
				
				c.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
				c.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
				c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
				c.set(Calendar.HOUR_OF_DAY, 0);
		    	c.set(Calendar.MINUTE, 0);
		    	c.set(Calendar.SECOND, 0);
		    	endOfMonth.setTime(c.getTimeInMillis());
				
				Map<String, Long> countForAllStatusToday = allTickets.stream()
						.filter(e -> e.getRecentActivityDate().after(startOfMonth) && e.getRecentActivityDate().before(endOfMonth))
						.collect(Collectors.groupingBy(Ticket :: getStatus, Collectors.counting()));
				
				returnMap.put("ticketStatistics", countForAllStatusToday);
				
				//get Last three Pending Tickets
				List<Ticket> recentTicketList = allTickets.stream()
				.filter(e -> !e.getStatus().equals(ApplicationConstants.TicketConstants.TICKET_STATUS_COMPLETED) && !e.getStatus().equals(ApplicationConstants.TicketConstants.TICKET_STATUS_CANCELLED))
				.sorted(Comparator.comparing(Ticket :: getRecentActivityDate).reversed())
				.collect(Collectors.toList());
				
				returnMap.put("recentThreeTickets", recentTicketList.subList(0, 3));
				
				List<Enquiry> enquiryList = null;
				enquiryList = enqRepo.findAll();
				
				Map<String, Long> countEnqForAllStatusToday = enquiryList.stream()
						.filter(e -> e.getRecentActivityDate().after(startOfMonth) && e.getRecentActivityDate().before(endOfMonth))
						.collect(Collectors.groupingBy(Enquiry :: getStatus, Collectors.counting()));
				
				returnMap.put("enquiryStatistics", countEnqForAllStatusToday);
				
				//get Last three Pending Enquiries
				List<Enquiry> recentEnquiryList = enquiryList.stream()
				.filter(e -> !e.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_WON) && !e.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_CANCELLED) && !e.getStatus().equals(ApplicationConstants.EnquiryConstants.ENQUIRY_STATUS_LOST))
				.sorted(Comparator.comparing(Enquiry :: getRecentActivityDate).reversed())
				.collect(Collectors.toList());
				
				returnMap.put("recentThreeEnquiries", recentEnquiryList.subList(0, 3));
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			}
			
		}
		return returnMap;
	}
	
}
