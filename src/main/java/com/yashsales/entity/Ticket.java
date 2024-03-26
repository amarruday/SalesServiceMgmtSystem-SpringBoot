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

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Timestamp getLastServiceDate() {
		return lastServiceDate;
	}

	public void setLastServiceDate(Timestamp lastServiceDate) {
		this.lastServiceDate = lastServiceDate;
	}

	public Timestamp getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Timestamp warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public boolean isInWarranty() {
		return isInWarranty;
	}

	public void setInWarranty(boolean isInWarranty) {
		this.isInWarranty = isInWarranty;
	}

	public String getMachineSerialNumber() {
		return machineSerialNumber;
	}

	public void setMachineSerialNumber(String machineSerialNumber) {
		this.machineSerialNumber = machineSerialNumber;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Timestamp getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Timestamp addedDate) {
		this.addedDate = addedDate;
	}

	public Timestamp getRecentActivityDate() {
		return recentActivityDate;
	}

	public void setRecentActivityDate(Timestamp recentActivityDate) {
		this.recentActivityDate = recentActivityDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public User getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(User assignedBy) {
		this.assignedBy = assignedBy;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	
}
