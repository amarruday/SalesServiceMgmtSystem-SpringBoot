package com.yashsales.service;

import java.util.Map;

import com.yashsales.dto.AddUserBean;
import com.yashsales.dto.UserBean;
import com.yashsales.entity.User;

public interface UserService {
	public UserBean createUser(AddUserBean addUserBean) throws Exception;
	public UserBean getUserByUsername(String username) throws Exception;
	public User getCurrentLoggedInUser();
	public UserBean createAdmin(UserBean userBean) throws Exception;
	public void resetPassword(Map<String, Object> payload) throws Exception;
	public User getUserByUserId(Long userId);
	public Map<String, Object> getUserByUserIdMap(Long userId);
	public Map<String, Object> getCRMUsers();
	public Map<String, Object> getAllUsers();
	public Map<String, Object> getUserDataForEditUser(Long userId);
	public Map<String, Object> updateUser(AddUserBean updateUserBean);
	public Map<String, Object> getSRMUser();
}
