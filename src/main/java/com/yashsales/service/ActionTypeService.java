package com.yashsales.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yashsales.entity.ActionType;

@Service
public interface ActionTypeService {

	public Map<String, Object> getAllActiveActionTypes();
	public Map<String, Object> getAllActionTypes();
	public Map<String, Object> addActionType(ActionType actionTypeBean);
	public Map<String, Object> updateActionType(ActionType actionTypeBean);
	public Map<String, Object> getActionTypeById(Long actionTypeId);
	public Map<String, Object> deleteActionType(Long actionTypeId);
}
