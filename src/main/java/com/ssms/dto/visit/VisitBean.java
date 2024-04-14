package com.ssms.dto.visit;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ssms.entity.ActionType;
import com.ssms.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class VisitBean {
	private Long visitId;
	private Customer customer;
	private Long customerId;
	private ActionType actionType;
	private Long actionTypeId;
	private String actionTypeName;
	private String description;
	private Long userId;
	private String addedByName;
	private Timestamp addedDate;
}