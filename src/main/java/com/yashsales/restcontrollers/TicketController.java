package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.outputbeans.AddTicketBean;
import com.yashsales.outputbeans.TicketSearchBean;
import com.yashsales.service.TicketService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/ticket")
@CrossOrigin("*")
public class TicketController {

	private final TicketService ticketService;

	@PostMapping("/")
	public Map<String, Object> addTicket(@RequestBody AddTicketBean addTicketBean) {
		return ticketService.addTicket(addTicketBean);
	}

	@PostMapping("/get")
	public Map<String, Object> getTickets(@RequestBody TicketSearchBean searchBean) {
		return ticketService.getPaginatedTickets(searchBean);
	}

	@GetMapping("/{ticketId}")
	public Map<String, Object> getTicketDetails(@PathVariable Long ticketId) {
		return ticketService.getTicketDetails(ticketId);
	}

	@PostMapping("/changestatus")
	public Map<String, Object> changeStatus(@RequestBody Map<String, Object> changeStatusBean) {
		return ticketService.changeTicketStatus(changeStatusBean);
	}
	
	@GetMapping("/getproductdetails/{customerId}")
	public Map<String, Object> getProductDetailsByCustomer(@PathVariable Long customerId) {
		return ticketService.getProductListByCustomer(customerId);
	}

}
