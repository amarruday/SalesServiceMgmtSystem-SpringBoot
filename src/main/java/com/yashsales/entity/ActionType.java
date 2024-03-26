package com.yashsales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "action_types")
public class ActionType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long actionTypeId;
	private String actionType;
	private String status;

	public Long getActionTypeId() {
		return actionTypeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setActionTypeId(Long actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

}
