package com.ssms.restcontrollers;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssms.entity.TicketType;
import com.ssms.service.impl.TicketTypeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/tickettypes")
@CrossOrigin("*")
public class TicketTypeController {
	
	private final TicketTypeService ticketTypeService;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/")
	public Map<String, Object> getAllTicketTypes() {
		return ticketTypeService.getAllTicketTypes();
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/all/active")
	public Map<String, Object> getAllActiveTicketTypes() {
		return ticketTypeService.getAllActiveTicketTypes();
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/{ticketTypeId}")
	public Map<String, Object> getTicketTypeById(@PathVariable Long ticketTypeId) {
		return ticketTypeService.getTicketTypeById(ticketTypeId);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/")
	public Map<String, Object> addTicketType(@RequestBody TicketType ticketType) {
		return ticketTypeService.addTicketType(ticketType);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/")
	public Map<String, Object> updateTicketType(@RequestBody TicketType ticketType) {
		return ticketTypeService.updateTicketType(ticketType);
	}
	
}
