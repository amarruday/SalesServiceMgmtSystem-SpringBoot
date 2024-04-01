package com.yashsales.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.entity.ActionType;
import com.yashsales.repository.ActionTypeRepository;
import com.yashsales.service.ActionTypeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActionTypeServiceImpl implements ActionTypeService {

	private final ActionTypeRepository actionTypeRepo;

	@Override
	public Map<String, Object> getAllActiveActionTypes() {
		List<ActionType> actionTypesList = null;
		Map<String, Object> responseMap = new HashMap<>();
		actionTypesList = actionTypeRepo.findAllByStatus(ApplicationConstants.CustomerConstants.ACTION_TYPE_ACTIVE);
		responseMap.put("ActionTypeList", null);
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		if (actionTypesList != null && actionTypesList.size() > 0) {
			responseMap.put("ActionTypeList", actionTypesList);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> addActionType(ActionType actionTypeBean) {
		List<ActionType> actionTypes = null;
		actionTypes = actionTypeRepo.findAll();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		Optional<ActionType> queryResult = actionTypes.stream().filter(
				actionType -> actionType.getActionType().trim().equalsIgnoreCase(actionTypeBean.getActionType().trim()))
				.findFirst();

		if (queryResult.isPresent()) {
			responseMap.put("message", "This action type already exists!");
			responseMap.put("errorCode", "WSEC001");
		} else {
			ActionType actionType = new ActionType();
			actionType.setActionType(actionTypeBean.getActionType().trim());
			actionType.setStatus(ApplicationConstants.CustomerConstants.ACTION_TYPE_ACTIVE);
			actionTypeRepo.save(actionType);
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("message", "Action type added.");
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> updateActionType(ActionType actionTypeBean) {
		ActionType theActionType = null;

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("message", "Failed to update action type!");

		if (actionTypeBean.getActionTypeId() != null && actionTypeBean.getActionTypeId() > 0) {
			theActionType = actionTypeRepo.findById(actionTypeBean.getActionTypeId()).get();

			if (theActionType != null && theActionType.getActionTypeId() > 0) {
				theActionType.setStatus(actionTypeBean.getStatus());
				actionTypeRepo.save(theActionType);
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseMap.put("message", "Action type updated succesfully!");
			}
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getActionTypeById(Long actionTypeId) {
		ActionType actionType = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("message", "Failed to get action type!");

		if (actionTypeId != null & actionTypeId > 0) {
			actionType = actionTypeRepo.findById(actionTypeId).orElse(null);
			if (actionType != null && actionType.getActionTypeId() > 0) {
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseMap.put("message", "");
				responseMap.put("ActionType", actionType);
			} else {
				responseMap.put("message", "No action type of id " + actionTypeId + " found!");
			}
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> deleteActionType(Long actionTypeId) {
		ActionType actionType = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("message", "Failed to delete action type!");

		if (actionTypeId != null & actionTypeId > 0) {
			actionType = actionTypeRepo.findById(actionTypeId).orElse(null);
			if (actionType != null && actionType.getActionTypeId() > 0) {
				actionTypeRepo.delete(actionType);
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseMap.put("message", "Action type deleted successfully!");
			}
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getAllActionTypes() {
		List<ActionType> actionTypesList = null;
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("ActionTypeList", null);
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		actionTypesList = actionTypeRepo.findAll();
		responseMap.put("ActionTypeList", actionTypesList);
		return responseMap;
	}

}