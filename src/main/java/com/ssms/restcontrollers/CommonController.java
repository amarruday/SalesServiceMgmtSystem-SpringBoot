package com.ssms.restcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssms.entity.ActionType;
import com.ssms.entity.CommonReply;
import com.ssms.entity.Department;
import com.ssms.entity.EnquirySource;
import com.ssms.service.ActionTypeService;
import com.ssms.service.CommonService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/company/")
@CrossOrigin("*")
public class CommonController {
	
	//Construction Injection - Lombok will create All args constructor
	private final CommonService commonService;
	private final ActionTypeService actionTypeService;

	//ActionTypes
	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("actionType/{status}")
	public ResponseEntity<Map<String, Object>> getActionTypes(@PathVariable("status") String status) {
		Map<String, Object> responseMap = null;
		if(status.equals("All")) {
			responseMap = actionTypeService.getAllActionTypes();
		}
		
		if(status.equals("Active")) {
			responseMap =  actionTypeService.getAllActiveActionTypes();
		}
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("actionType/{actionTypeId}")
	public ResponseEntity<Map<String, Object>> getActionTypeById(@PathVariable Long actionTypeId) {
		return new ResponseEntity<>(actionTypeService.getActionTypeById(actionTypeId), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("actionType/")
	public ResponseEntity<Map<String, Object>> addActionType(@RequestBody ActionType actionTypeBean) {
		return new ResponseEntity<>(actionTypeService.addActionType(actionTypeBean), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("actionType/")
	public ResponseEntity<Map<String, Object>> updateActionType(@RequestBody ActionType actionTypeBean) {
		return new ResponseEntity<>(actionTypeService.updateActionType(actionTypeBean), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("actionType/{actionTypeId}")
	public ResponseEntity<Map<String, Object>> deleteActionType(@PathVariable Long actionTypeId) {
		return new ResponseEntity<>(actionTypeService.deleteActionType(actionTypeId), HttpStatus.OK);
	}

	//CommonReplies
	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("commonReply")
	public ResponseEntity<Map<String, Object>> getCommonReplies() {
		return new ResponseEntity<>(commonService.getCommonReplies(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("commonReply")
	public ResponseEntity<Map<String, Object>> addCommonReply(@RequestBody CommonReply commonReply) {
		return new ResponseEntity<>(commonService.addCommonReply(commonReply), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("commonReply/{commonReplyId}")
	public ResponseEntity<Map<String, Object>> deleteCommonReply(@PathVariable Long commonReplyId) {
		return new ResponseEntity<>(commonService.deleteCommonReply(commonReplyId), HttpStatus.OK);
	}
	
	//departments
	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("department")
	public ResponseEntity<List<Department>> getDepartments() {
		return new ResponseEntity<>(commonService.getDepartments(), HttpStatus.OK);
	}
	
	//Enquiry Source
	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("enquirySources")
	public ResponseEntity<Map<String, Object>> getEnquirySources() {
		return new ResponseEntity<>(commonService.getEnquirySources(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("enquirySources")
	public ResponseEntity<Map<String, Object>> addEnquirySource(@RequestBody EnquirySource enquirySource) {
		return new ResponseEntity<>(commonService.addEnquirySource(enquirySource), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("enquirySources/{enquirySourceId}")
	public ResponseEntity<Map<String, Object>> deleteEnquirySource(@PathVariable Long enquirySourceId) {
		return null;
	}
	
	//Enquiry Types
	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("enquiryType")
	public ResponseEntity<Map<String, Object>> getEnquiryTypes() {
		return new ResponseEntity<>(commonService.getEnquiryTypes(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("role")
 	public Map<String, Object> getRoles() {
		return commonService.getRoles();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getsettingsbyrole/{roleId}")
	public Map<String, Object> getSettingsByRole(@PathVariable Long roleId) {
		return commonService.getSettingsByRole(roleId);
	}
	
}
