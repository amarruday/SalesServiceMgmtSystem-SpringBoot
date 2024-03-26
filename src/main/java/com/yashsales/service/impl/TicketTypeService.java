package com.yashsales.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.entity.Customer;
import com.yashsales.entity.CustomerProductLink;
import com.yashsales.entity.TicketType;
import com.yashsales.repository.CustomerProudctLinkRepository;
import com.yashsales.repository.CustomerRepository;
import com.yashsales.repository.TicketTypeRepository;

@Service
public class TicketTypeService {

	@Autowired
	private TicketTypeRepository ticketTypeRepo;
	

	
	@Autowired
	private CustomerRepository custRepo;
	
	public Map<String, Object> getAllTicketTypes() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<TicketType> ticketTypeList = null;
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		ticketTypeList = ticketTypeRepo.findAll();
		returnMap.put("message", "");
		returnMap.put("TicketTypeList", ticketTypeList);
		return returnMap;
	}

	public Map<String, Object> getAllActiveTicketTypes() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<TicketType> ticketTypeList = null;
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		ticketTypeList = ticketTypeRepo.findAllByStatus(ApplicationConstants.TicketConstants.TICKET_TYPE_STATUS_ACTIVE);
		returnMap.put("message", "");
		returnMap.put("TicketTypeList", ticketTypeList);
		return returnMap;
	}

	public Map<String, Object> getTicketTypeById(Long ticketTypeId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		TicketType ticketType = null;
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		ticketType = ticketTypeRepo.findById(ticketTypeId).orElse(null);
		if(ticketType != null && ticketType.getTicketTypeId() > 0) {
			returnMap.put("message", "");
			returnMap.put("TicketType", ticketType);
			returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		}
		return returnMap;
	}

	public Map<String, Object> addTicketType(TicketType ticketType) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		ticketType.setStatus("Active");
		ticketType = ticketTypeRepo.save(ticketType);
		if(ticketType != null && ticketType.getTicketTypeId() > 0) {
			returnMap.put("message", "Ticket type added successfully!");
			returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		}		
		return returnMap;
	}

	public Map<String, Object> updateTicketType(TicketType ticketType) {
		TicketType localTicketType = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		localTicketType = ticketTypeRepo.findById(ticketType.getTicketTypeId()).orElse(null);
		if(localTicketType != null && localTicketType.getTicketTypeId() > 0) {
			localTicketType.setTicketType(ticketType.getTicketType());
			localTicketType.setStatus(ticketType.getStatus());
			ticketTypeRepo.save(localTicketType);
			returnMap.put("message", "Ticket type updated successfully!");
			returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		}
		return returnMap;
	}
}
