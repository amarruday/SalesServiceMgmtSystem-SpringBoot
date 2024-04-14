package com.ssms.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ticketId;

	@OneToOne
	@JoinColumn(name = "ticketTypeId")
	private TicketType ticketType;

	@OneToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "productId")
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

	@OneToOne
	@JoinColumn(name = "ADDED_BY", referencedColumnName = "userId")
	private User addedBy;

	@OneToOne
	@JoinColumn(name = "ASSIGNED_BY", referencedColumnName = "userId")
	private User assignedBy;

	@OneToOne
	@JoinColumn(name = "ASSIGNED_TO", referencedColumnName = "userId")
	private User assignedTo;	
}
