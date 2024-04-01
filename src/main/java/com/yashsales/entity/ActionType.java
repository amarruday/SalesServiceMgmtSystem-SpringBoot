package com.yashsales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "action_types")
@AllArgsConstructor
@NoArgsConstructor
public class ActionType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long actionTypeId;
	private String actionType;
	private String status;

}
