package com.yashsales.outputbeans;

import java.util.Date;

public class SearchVisitBean {

	private Date startDate;
	private Date endDate;
	private Long actionTypeId;
	private Long userId;
	private Long customerId;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getActionTypeId() {
		return actionTypeId;
	}

	public void setActionTypeId(Long actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "SearchVisitBean [startDate=" + startDate + ", endDate=" + endDate + ", actionTypeId=" + actionTypeId
				+ ", userId=" + userId + ", customerId=" + customerId + "]";
	}

}
