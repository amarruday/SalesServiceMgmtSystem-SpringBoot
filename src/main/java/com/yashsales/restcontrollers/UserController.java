package com.yashsales.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.entity.User;
import com.yashsales.outputbeans.AddUserBean;
import com.yashsales.outputbeans.BaseResponse;
import com.yashsales.outputbeans.UserBean;
import com.yashsales.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/user/")
@CrossOrigin("*")
public class UserController {

	private final UserService userService;

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
	@GetMapping("{username}")
	public UserBean getUserByUsername(@PathVariable("username") String username) throws Exception {
		return userService.getUserByUsername(username);
	}

	@GetMapping("/getforedit/{userId}")
	public Map<String, Object> getUserDataForEditUser(@PathVariable Long userId) {
		return userService.getUserDataForEditUser(userId);
	}
	
	@PutMapping("/")
	public Map<String, Object> updateUser(@RequestBody AddUserBean updateUserBean) {
		return userService.updateUser(updateUserBean);
	}
	
	@GetMapping("/get/{userId}")
	public Map<String, Object> getUserByUserId(@PathVariable Long userId) {		
		return userService.getUserByUserIdMap(userId);
	}

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

	@PostMapping("/resetPassword")
	public void resetPassword() throws Exception {
		 Map<String, Object> payload = null;
		 userService.resetPassword(payload);
	}

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

	@GetMapping("/getcrmusers")
	public Map<String, Object> getCRMUsers() {
		return userService.getCRMUsers();
	}
	
	@GetMapping("/getsrmusers")
	public Map<String, Object> getSRMUsers() {
		return userService.getSRMUser();
	}

	@GetMapping("/all")
	public Map<String, Object> getAllUsers() {
		return userService.getAllUsers();
	}

}