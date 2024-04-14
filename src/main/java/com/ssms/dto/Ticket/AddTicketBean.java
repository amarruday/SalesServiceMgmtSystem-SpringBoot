package com.ssms.dto.Ticket;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddTicketBean {
	private Long ticketTypeId;
	private Long customerId;
	private Long productId;
	private String priority;
	private Date lastServiceDate;
	private Date warrantyDate;
	private boolean isInWarranty;
	private String machineSerialNumber;
	private String shortDescription;
	private String longDescription;
	private Long assignedById;
	private Long assignToId;
}