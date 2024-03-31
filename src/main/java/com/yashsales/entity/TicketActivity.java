package com.yashsales.entity;

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
@Table(name = "ticket_activities")
public class TicketActivity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ticketActivityId;

	@OneToOne
	@JoinColumn(name = "ticketId")
	private Ticket ticket;

	@OneToOne
	@JoinColumn(name = "userId")
	private User user;

	private String status;
	private Timestamp recordDate;
	private String remark;
}
