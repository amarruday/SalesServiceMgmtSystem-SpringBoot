package com.yashsales.outputbeans;

import java.sql.Timestamp;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yashsales.entity.ActionType;
import com.yashsales.entity.Customer;
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
	@NonNull
	private Long customerId;

	private ActionType actionType;
	@NonNull
	private Long actionTypeId;
	private String actionTypeName;

	private String description;

	@NonNull
	private Long userId;
	private String addedByName;
	private Timestamp addedDate;
}