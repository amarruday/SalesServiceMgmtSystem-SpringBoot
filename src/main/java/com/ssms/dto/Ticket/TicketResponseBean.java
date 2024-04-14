package com.ssms.dto.Ticket;

import java.sql.Timestamp;

import com.ssms.entity.Customer;
import com.ssms.entity.Product;
import com.ssms.entity.TicketType;
import com.ssms.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseBean {
	private Long ticketId;
	private TicketType ticketType;
	private Customer customer;
	private Product product;
	private String priority;
	private Timestamp lastServiceDate;
	private Timestamp warrantyDate;
	private boolean isInWarranty;
	private String machineSerialNumber;
	private String shortDescription;
	private String longDescription;
	private Timestamp addedDate;
	private Timestamp recentActivityDate;
	private String status;
	private User addedBy;
	private User assignedBy;
	private User assignedTo;	
}
