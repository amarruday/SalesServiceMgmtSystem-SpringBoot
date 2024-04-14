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
public class TicketSearchBean {
	private int pageNumber;
	private int pageSize;
	private Date startDate;
	private Date endDate;
	private Long ticketTypeId;
	private String priority;
	private String status;
	private Long customerId;
	private Long addedBy;
}
