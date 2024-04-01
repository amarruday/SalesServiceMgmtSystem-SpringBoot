package com.yashsales.restcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.entity.ActionType;
import com.yashsales.entity.Brand;
import com.yashsales.entity.CommonReply;
import com.yashsales.entity.Department;
import com.yashsales.entity.EnquirySource;
import com.yashsales.service.ActionTypeService;
import com.yashsales.service.CommonService;

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

	@GetMapping("actionType/{actionTypeId}")
	public ResponseEntity<Map<String, Object>> getActionTypeById(@PathVariable Long actionTypeId) {
		return new ResponseEntity<>(actionTypeService.getActionTypeById(actionTypeId), HttpStatus.OK);
	}

	@PostMapping("actionType/")
	public ResponseEntity<Map<String, Object>> addActionType(@RequestBody ActionType actionTypeBean) {
		return new ResponseEntity<>(actionTypeService.addActionType(actionTypeBean), HttpStatus.CREATED);
	}

	@PutMapping("actionType/")
	public ResponseEntity<Map<String, Object>> updateActionType(@RequestBody ActionType actionTypeBean) {
		return new ResponseEntity<>(actionTypeService.updateActionType(actionTypeBean), HttpStatus.OK);
	}

	@DeleteMapping("actionType/{actionTypeId}")
	public ResponseEntity<Map<String, Object>> deleteActionType(@PathVariable Long actionTypeId) {
		return new ResponseEntity<>(actionTypeService.deleteActionType(actionTypeId), HttpStatus.OK);
	}
	
	//Brands
	@GetMapping("brand/")
	public ResponseEntity<Map<String, Object>> getBrands() {
		return new ResponseEntity<>(commonService.getBrands(), HttpStatus.OK);
	}
	
	@GetMapping("brand/Active")
	public ResponseEntity<Map<String, Object>> getActiveBrands() {
		return new ResponseEntity<>(commonService.getActiveBrands(), HttpStatus.OK);
	}

	@GetMapping("brand/{brandId}")
	public ResponseEntity<Map<String, Object>> getBrandById(@PathVariable Long brandId) {
		return new ResponseEntity<>(commonService.getBrand(brandId), HttpStatus.OK);
	}
	
	@PostMapping("brand/")
	public ResponseEntity<Map<String, Object>> addBrand(@RequestBody Brand brand) {
		return new ResponseEntity<>(commonService.addBrand(brand), HttpStatus.CREATED);
	}
	
	@PutMapping("brand/")
	public ResponseEntity<Map<String, Object>> updateBrand(@RequestBody Brand brand) {
		return new ResponseEntity<>(commonService.editBrand(brand), HttpStatus.OK);
	}
	
	//CommonReplies
	@GetMapping("commonReply")
	public ResponseEntity<Map<String, Object>> getCommonReplies() {
		return new ResponseEntity<>(commonService.getCommonReplies(), HttpStatus.OK);
	}
	
	@PostMapping("commonReply")
	public ResponseEntity<Map<String, Object>> addCommonReply(@RequestBody CommonReply commonReply) {
		return new ResponseEntity<>(commonService.addCommonReply(commonReply), HttpStatus.CREATED);
	}
	
	@DeleteMapping("commonReply/{commonReplyId}")
	public ResponseEntity<Map<String, Object>> deleteCommonReply(@PathVariable Long commonReplyId) {
		return new ResponseEntity<>(commonService.deleteCommonReply(commonReplyId), HttpStatus.OK);
	}
	
	//departments
	@GetMapping("department")
	public ResponseEntity<List<Department>> getDepartments() {
		return new ResponseEntity<>(commonService.getDepartments(), HttpStatus.OK);
	}
	
	//Enquiry Source
	@GetMapping("enquirySources")
	public ResponseEntity<Map<String, Object>> getEnquirySources() {
		return new ResponseEntity<>(commonService.getEnquirySources(), HttpStatus.OK);
	}

	@PostMapping("enquirySources")
	public ResponseEntity<Map<String, Object>> addEnquirySource(@RequestBody EnquirySource enquirySource) {
		return new ResponseEntity<>(commonService.addEnquirySource(enquirySource), HttpStatus.OK);
	}
	
	@DeleteMapping("enquirySources/{enquirySourceId}")
	public ResponseEntity<Map<String, Object>> deleteEnquirySource(@PathVariable Long enquirySourceId) {
		return null;
	}
	
	//Enquiry Types
	@GetMapping("enquiryType")
	public ResponseEntity<Map<String, Object>> getEnquiryTypes() {
		return new ResponseEntity<>(commonService.getEnquiryTypes(), HttpStatus.OK);
	}
		
	@GetMapping("role")
 	public Map<String, Object> getRoles() {
		return commonService.getRoles();
	}

	@GetMapping("/getsettingsbyrole/{roleId}")
	public Map<String, Object> getSettingsByRole(@PathVariable Long roleId) {
		return commonService.getSettingsByRole(roleId);
	}
	
}
