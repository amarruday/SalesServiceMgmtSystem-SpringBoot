package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.entity.TicketType;
import com.yashsales.service.impl.TicketTypeService;

@RestController
@RequestMapping("/tickettypes")
@CrossOrigin("*")
public class TicketTypeController {
	
	@Autowired
	private TicketTypeService ticketTypeService;
	
	@GetMapping("/")
	public Map<String, Object> getAllTicketTypes() {
		return ticketTypeService.getAllTicketTypes();
	}
	
	@GetMapping("/all/active")
	public Map<String, Object> getAllActiveTicketTypes() {
		return ticketTypeService.getAllActiveTicketTypes();
	}

	@GetMapping("/{ticketTypeId}")
	public Map<String, Object> getTicketTypeById(@PathVariable Long ticketTypeId) {
		return ticketTypeService.getTicketTypeById(ticketTypeId);
	}
	
	@PostMapping("/")
	public Map<String, Object> addTicketType(@RequestBody TicketType ticketType) {
		return ticketTypeService.addTicketType(ticketType);
	}
		
	@PutMapping("/")
	public Map<String, Object> updateTicketType(@RequestBody TicketType ticketType) {
		return ticketTypeService.updateTicketType(ticketType);
	}
	
}
