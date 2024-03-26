package com.yashsales.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

	public Long getTicketActivityId() {
		return ticketActivityId;
	}

	public void setTicketActivityId(Long ticketActivityId) {
		this.ticketActivityId = ticketActivityId;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
