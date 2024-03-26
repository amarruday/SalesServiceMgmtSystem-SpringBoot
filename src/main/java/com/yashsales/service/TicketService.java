package com.yashsales.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yashsales.outputbeans.AddTicketBean;
import com.yashsales.outputbeans.TicketSearchBean;

@Service
public interface TicketService {
	public abstract Map<String, Object> addTicket(AddTicketBean addTicketBean);
	public abstract Map<String, Object> getPaginatedTickets(TicketSearchBean searchBean);
	public abstract Map<String, Object> getTicketDetails(Long ticketId);
	public abstract Map<String, Object> changeTicketStatus(Map<String, Object> changeStatusBean);
	public abstract Map<String, Object> getProductListByCustomer(Long customerId);
}
