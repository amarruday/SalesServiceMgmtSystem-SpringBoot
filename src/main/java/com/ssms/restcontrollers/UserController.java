package com.ssms.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssms.constants.ApplicationConstants;
import com.ssms.dto.commons.AddUserBean;
import com.ssms.dto.commons.BaseResponse;
import com.ssms.dto.commons.UserBean;
import com.ssms.entity.User;
import com.ssms.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/user/")
@CrossOrigin("*")
public class UserController {

	private final UserService userService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("create")
	public Map<String, Object> createUser(@RequestBody AddUserBean addUserBean) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		returnMap.put("message", "Failed to add user! try again");
		returnMap.put("errorCode", "WSEM006");
		UserBean user = userService.createUser(addUserBean);
		if(user != null && user.getUserId() > 0) {
			returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			returnMap.put("message", "New user added!");
			returnMap.put("errorCode", null);
		}
		return returnMap;
	}

	// Get User by username
	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("{username}")
	public UserBean getUserByUsername(@PathVariable("username") String username) throws Exception {
		return userService.getUserByUsername(username);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getforedit/{userId}")
	public Map<String, Object> getUserDataForEditUser(@PathVariable Long userId) {
		return userService.getUserDataForEditUser(userId);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/")
	public Map<String, Object> updateUser(@RequestBody AddUserBean updateUserBean) {
		return userService.updateUser(updateUserBean);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/get/{userId}")
	public Map<String, Object> getUserByUserId(@PathVariable Long userId) {		
		return userService.getUserByUserIdMap(userId);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/currentloggedinuser")
	public Map<String, Object> getCurrentLoggedInUser() {
		User user = null;
		UserBean userBean = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		
		try {
			user = userService.getCurrentLoggedInUser();
			userBean = userService.getUserByUsername(user.getUsername());
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("User", userBean);
		} catch (Exception e) {
			// nothing to do
		}
		return responseMap;
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@PostMapping("/resetPassword")
	public void resetPassword() throws Exception {
		 Map<String, Object> payload = null;
		 userService.resetPassword(payload);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/addAdmin")
	public BaseResponse addAdmin(@RequestBody UserBean userBean) {
		BaseResponse response = new BaseResponse();
		try {
			return userService.createAdmin(userBean);
		} catch (Exception e) {
			response.setMessage("This User is Already Exists!");
			return response;
		}
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/getcrmusers")
	public Map<String, Object> getCRMUsers() {
		return userService.getCRMUsers();
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	@GetMapping("/getsrmusers")
	public Map<String, Object> getSRMUsers() {
		return userService.getSRMUser();
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SALES_MANAGER', 'SALES_ENGINEER', 'SERVICE_MANAGER', 'SERVICE_ENGINEER')")
	public Map<String, Object> getAllUsers() {
		return userService.getAllUsers();
	}

}