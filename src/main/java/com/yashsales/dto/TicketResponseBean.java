package com.yashsales.dto;

import java.sql.Timestamp;

import com.yashsales.entity.Customer;
import com.yashsales.entity.Product;
import com.yashsales.entity.TicketType;
import com.yashsales.entity.User;

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
