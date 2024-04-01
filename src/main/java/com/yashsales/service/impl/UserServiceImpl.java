package com.yashsales.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.dto.AddUserBean;
import com.yashsales.dto.RoleWiseManagersListBean;
import com.yashsales.dto.UserBean;
import com.yashsales.entity.Authority;
import com.yashsales.entity.Department;
import com.yashsales.entity.ProductCatagory;
import com.yashsales.entity.Role;
import com.yashsales.entity.User;
import com.yashsales.entity.UserProductCatagoryLink;
import com.yashsales.repository.DepartmentRepository;
import com.yashsales.repository.ProductCatagoryRepository;
import com.yashsales.repository.RoleRepository;
import com.yashsales.repository.UserProductCatagoryLinkRepository;
import com.yashsales.repository.UserRepository;
import com.yashsales.service.UserService;

import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final DepartmentRepository deptRepo;
	private final ProductCatagoryRepository prodCatagoryRepo;
	private final UserProductCatagoryLinkRepository uProdCatRepo;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public UserBean createAdmin(UserBean userBean) throws Exception {
		Optional<Role> role = null;
		String encodedPassword = null;
		User localUser = this.userRepo.findByUsername(userBean.getUsername());
		if (localUser != null) {
			throw new Exception("This user already exists..!");
		} else {
			role = roleRepo.findById(userBean.getRoleId());
			if (role != null) {
				localUser = new User();
				localUser.setFullName(userBean.getFullName());
				localUser.setEmployeeCode(userBean.getEmployeeCode());
				localUser.setUsername(userBean.getEmail());
				encodedPassword = passwordEncoder.encode(userBean.getPassword());
				localUser.setPassword(encodedPassword);
				localUser.setEmail(userBean.getEmail());
				localUser.setMobileNumber(userBean.getMobileNumber());
				localUser.setProfilePic("/default.png");
				localUser.setStatus(true);
				localUser.setJoiningDate("05/11/2021");
				localUser.setCreatedBy(ApplicationConstants.RoleConstants.ROLE_ADMIN);
				// get department details from db
				Department department = deptRepo
						.findByDepartmentName(ApplicationConstants.DepartmentConstants.SALES_AND_SERIVCE);
				localUser.setDepartment(department);
				localUser.setRole(role.get());
				localUser = userRepo.save(localUser);
				userBean = getUserByUsername(localUser.getUsername());
			}
		}
		return userBean;
	}

	@Override
	@Transactional
	public Map<String, Object> updateUser(AddUserBean updateUserBean) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		User localUser = this.userRepo.findById(updateUserBean.getUserId()).orElse(null);
		if (localUser != null && localUser.getUserId() > 0) {
			localUser.setFullName(updateUserBean.getFullName().trim());
			localUser.setEmployeeCode(updateUserBean.getEmployeeCode().trim());
			localUser.setEmail(updateUserBean.getEmail().trim());
			localUser.setUsername(updateUserBean.getEmail().trim());
			localUser.setMobileNumber(updateUserBean.getMobileNumber().trim());
			localUser.setStatus(updateUserBean.isStatus());
			
			Role role = roleRepo.findById(updateUserBean.getRoleId()).orElse(null);

			if (role != null && role.getRoleId() > 0) {
				localUser.setRole(role);
				Department department = null;
				if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)
						|| role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_ENGINEER)) {
					department = deptRepo
							.findByDepartmentName(ApplicationConstants.DepartmentConstants.SALES_DEPARMENT);
				}
				if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)
						|| role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_ENGINEER)) {
					department = deptRepo
							.findByDepartmentName(ApplicationConstants.DepartmentConstants.SERVICE_DEPARTMENT);
				}
				localUser.setDepartment(department);
			}

			// When role is Engineer
			if (updateUserBean.getManagerId() > 0) {
				User manager = userRepo.findById(updateUserBean.getManagerId()).get();
				if (manager.getUserId() > 0 && manager != null) {
					localUser.setManager(manager);
				}
			}

			if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER) || role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)) {
				if (updateUserBean.getProductCatagoryId() > 0) {
					// getProductCatagory
					ProductCatagory productCatagory = null;
					productCatagory = prodCatagoryRepo.findById(updateUserBean.getProductCatagoryId()).get();
					if (productCatagory.isProductCatagoryStatus() && productCatagory != null && productCatagory.getProductCatagoryId() > 0) {
						UserProductCatagoryLink upcLink = null;
						upcLink = uProdCatRepo.findByUserId(localUser.getUserId());
						if (upcLink != null && upcLink.getUserProductCatagoryLinkId() > 0) {
							upcLink.setUser(localUser);
							upcLink.setProductCatagory(productCatagory);
							upcLink.setStatus(true);
							upcLink.setUpdatedBy(ApplicationConstants.RoleConstants.ROLE_ADMIN);
							upcLink = uProdCatRepo.save(upcLink);
						}
					}
				}
			}
			
			returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			returnMap.put("message", "User updated successfully!");
		} else {
			returnMap.put("message", "failed to update user!");
		}
		return returnMap;
	}

	@Override
	public UserBean getUserByUsername(String username) throws Exception {
		User localUser = null;
		UserBean userBean = null;

		localUser = userRepo.findByUsername(username);
		if (localUser != null) {
			userBean = new UserBean();
			userBean.setUserId(localUser.getUserId());
			userBean.setFullName(localUser.getFullName());
			userBean.setEmployeeCode(localUser.getEmployeeCode());
			userBean.setUsername(localUser.getUsername());
			userBean.setPassword("");
			userBean.setStatus(localUser.isStatus());
			userBean.setEmail(localUser.getEmail());
			userBean.setMobileNumber(localUser.getMobileNumber());
			userBean.setProfilePic(localUser.getProfilePic());
			Set<Authority> authSet = new HashSet<>();
			authSet.add(new Authority(localUser.getRole().getRolename()));
			userBean.setRoleName(localUser.getRole().getRolename());
			userBean.setManager(localUser.getManager());
			userBean.setDepartment(localUser.getDepartment());
			userBean.setAuthorities(authSet);
			userBean.setJoiningDate(localUser.getJoiningDate());
			userBean.setCreatedDate(localUser.getCreatedDate());
			userBean.setUpdateDate(localUser.getUpdateDate());
			userBean.setResponseStatus(ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		} else {
			throw new Exception("User not found for given username!");
		}
		return userBean;
	}

	@Override
	public User getCurrentLoggedInUser()  {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = null;
		if(username != null && !username.equals("")) {
			user = userRepo.findByUsername(username);
		}
		return user;
	}

	@Override
	@Transactional
	public UserBean createUser(AddUserBean addUserBean) throws Exception {
		Role role = null;
		User localUser = this.userRepo.findByUsername(addUserBean.getEmail());

		if (localUser != null) {
			throw new Exception("This user already exists..!");
		} else {
			role = roleRepo.findById(addUserBean.getRoleId()).get();
			if (role != null) {
				localUser = new User();
				localUser.setRole(role);
				// When role is Engineer
				if (addUserBean.getManagerId() > 0) {
					User manager = userRepo.findById(addUserBean.getManagerId()).get();
					if (manager.getUserId() > 0 && manager != null) {
						localUser.setManager(manager);
					}
				}

				Department department = null;
				if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)
						|| role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_ENGINEER)) {
					department = deptRepo
							.findByDepartmentName(ApplicationConstants.DepartmentConstants.SALES_DEPARMENT);
				}
				if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)
						|| role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_ENGINEER)) {
					department = deptRepo
							.findByDepartmentName(ApplicationConstants.DepartmentConstants.SERVICE_DEPARTMENT);
				}
				localUser.setDepartment(department);
				localUser.setFullName(addUserBean.getFullName());
				localUser.setEmployeeCode(addUserBean.getEmployeeCode());
				localUser.setUsername(addUserBean.getEmail());
				String random = RandomString.make(6);
				String encodedPassword = passwordEncoder.encode(random);
				localUser.setPassword(encodedPassword);
				localUser.setEmail(addUserBean.getEmail());
				localUser.setMobileNumber(addUserBean.getMobileNumber());
				localUser.setJoiningDate("09/11/2021");
				localUser.setProfilePic("/default.png");
				localUser.setStatus(true);
				localUser.setCreatedBy(ApplicationConstants.RoleConstants.ROLE_ADMIN);
				localUser = userRepo.save(localUser);

				if (localUser.getRole().getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)
						|| localUser.getRole().getRolename()
								.equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)) {

					if (addUserBean.getProductCatagoryId() > 0) {
						// getProductCatagory
						ProductCatagory productCatagory = null;
						productCatagory = prodCatagoryRepo.findById(addUserBean.getProductCatagoryId()).get();
						if (productCatagory.isProductCatagoryStatus() && productCatagory != null
								&& productCatagory.getProductCatagoryId() > 0) {
							UserProductCatagoryLink upcLink = new UserProductCatagoryLink();
							upcLink.setUser(localUser);
							upcLink.setProductCatagory(productCatagory);
							upcLink.setStatus(true);
							upcLink.setCreatedBy(ApplicationConstants.RoleConstants.ROLE_ADMIN);
							upcLink = uProdCatRepo.save(upcLink);
						} else {
							throw new Exception("Something went wrong with Product Catagory");
						}
					}
				}
			}
		}
		return getUserByUsername(localUser.getUsername());
	}

	@Override
	public void resetPassword(Map<String, Object> payload) throws Exception {
		
		List<User> usersList = userRepo.findAll();
		String newPassword = "0000";
		String encodedPassword = passwordEncoder.encode(newPassword);
		
		for(int i = 0; i < usersList.size(); i++) {
			User user = userRepo.findById(usersList.get(i).getUserId()).orElse(null);
			user.setPassword(encodedPassword);
			userRepo.save(user);
			System.out.println("Password Updated for " + user.getFullName());
		}

	}

	@Override
	public User getUserByUserId(Long userId) {
		User user = null;
		if (userId > 0) {
			user = userRepo.findById(userId).get();
		}
		return user;
	}

	@Override
	public Map<String, Object> getCRMUsers() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<UserBean> userBeanList = new ArrayList<UserBean>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("UsersList", null);
		Department department = deptRepo.findByDepartmentName(ApplicationConstants.DepartmentConstants.SALES_DEPARMENT);
		List<User> usersList = userRepo.findByDepartment(department);

		for (int i = 0; i < usersList.size(); i++) {
			UserBean userBean = new UserBean();
			userBean.setUserId(usersList.get(i).getUserId());
			userBean.setFullName(usersList.get(i).getFullName());
			userBean.setRoleName(usersList.get(i).getRole().getRolename());
			userBeanList.add(userBean);
		}

		if (userBeanList != null && userBeanList.size() > 0) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("UsersList", userBeanList);
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getAllUsers() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		returnMap.put("Users", null);
		List<User> usersList = null;

		usersList = userRepo.findAll();
		returnMap.put("Users", usersList);
		return returnMap;
	}

	@Override
	public Map<String, Object> getUserDataForEditUser(Long userId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		User localUser = null;

		UserBean userBean = null;
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		localUser = userRepo.findById(userId).orElse(null);
		if (localUser != null && localUser.getUserId() > 0) {
			userBean = new UserBean();
			userBean.setUserId(localUser.getUserId());
			userBean.setFullName(localUser.getFullName());
			userBean.setEmployeeCode(localUser.getEmployeeCode());
			userBean.setUsername(localUser.getUsername());
			userBean.setPassword("");
			userBean.setStatus(localUser.isStatus());
			userBean.setEmail(localUser.getEmail());
			userBean.setMobileNumber(localUser.getMobileNumber());
			userBean.setProfilePic(localUser.getProfilePic());
			userBean.setManager(localUser.getManager());
			userBean.setRoleId(localUser.getRole().getRoleId());

			// If role is manager
			UserProductCatagoryLink upcl = uProdCatRepo.findByUserId(localUser.getUserId());
			if (upcl != null && upcl.getUserProductCatagoryLinkId() > 0) {
				userBean.setProductCatagoryId(upcl.getProductCatagory().getProductCatagoryId());
				// userBean.setProductCatagoryName
			}

			Role role = roleRepo.findById(localUser.getRole().getRoleId()).get();
			if (localUser.getManager() != null && localUser.getManager().getUserId() > 0) {
				if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_ENGINEER)
						|| role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_ENGINEER)) {
					List<UserProductCatagoryLink> userProductCatagoryLinkList = null;
					List<RoleWiseManagersListBean> roleWiseManagersBeanList = new ArrayList<RoleWiseManagersListBean>();

					userProductCatagoryLinkList = uProdCatRepo.findAll();
					if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_ENGINEER)) {
						userProductCatagoryLinkList.forEach(userProd -> {
							if (userProd.getUser().getRole().getRolename()
									.equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)) {
								RoleWiseManagersListBean roleWiseManagersBean = new RoleWiseManagersListBean();
								roleWiseManagersBean.setManagerId(userProd.getUser().getUserId());
								String manager_ProductCatagory = userProd.getUser().getFullName() + " ["
										+ userProd.getUser().getRole().getRolename() + " of "
										+ userProd.getProductCatagory().getProductCatagoryName() + "]";
								roleWiseManagersBean.setManager_productCatagory(manager_ProductCatagory);
								roleWiseManagersBeanList.add(roleWiseManagersBean);
							}
						});
					}

					if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_ENGINEER)) {
						userProductCatagoryLinkList.forEach(userProd -> {
							if (userProd.getUser().getRole().getRolename()
									.equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)) {
								RoleWiseManagersListBean roleWiseManagersBean = new RoleWiseManagersListBean();
								roleWiseManagersBean.setManagerId(userProd.getUser().getUserId());
								String manager_ProductCatagory = userProd.getUser().getFullName() + " - "
										+ userProd.getProductCatagory().getProductCatagoryName();
								roleWiseManagersBean.setManager_productCatagory(manager_ProductCatagory);
								roleWiseManagersBeanList.add(roleWiseManagersBean);
							}
						});
					}
					returnMap.put("RoleWiseManagersList", roleWiseManagersBeanList);
				}
			} else {
				if (role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)
						|| role.getRolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER)) {
					List<ProductCatagory> productCatagoryList = null;
					productCatagoryList = prodCatagoryRepo.findByProductCatagoryStatus();

					returnMap.put("ProductCatagoryList", productCatagoryList);
				}
			}
			userBean.setJoiningDate(localUser.getJoiningDate());
			userBean.setCreatedDate(localUser.getCreatedDate());
			userBean.setUpdateDate(localUser.getUpdateDate());
			returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			returnMap.put("User", userBean);
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getUserByUserIdMap(Long userId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		User localUser = userRepo.findById(userId).orElse(null);
		UserBean userBean = new UserBean();
		if (localUser != null) {
			userBean = new UserBean();
			userBean.setUserId(localUser.getUserId());
			userBean.setFullName(localUser.getFullName());
			userBean.setEmployeeCode(localUser.getEmployeeCode());
			userBean.setUsername(localUser.getUsername());
			userBean.setPassword("");
			userBean.setStatus(localUser.isStatus());
			userBean.setEmail(localUser.getEmail());
			userBean.setMobileNumber(localUser.getMobileNumber());
			userBean.setProfilePic(localUser.getProfilePic());
			//Set<Authority> authSet = new HashSet<>();
			//authSet.add(new Authority(localUser.getRole().getRolename()));
			userBean.setRoleName(localUser.getRole().getRolename());
			if(localUser.getManager() != null && localUser.getManager().getUserId() > 0) {
				userBean.setManagerName(localUser.getManager().getFullName());
			} else  {
				UserProductCatagoryLink upcl = null;
				upcl = uProdCatRepo.findByUserId(localUser.getUserId());
				if(upcl != null) {
					userBean.setProductCatagoryName(upcl.getProductCatagory().getProductCatagoryName());
				}
			}
			
			userBean.setDepartment(localUser.getDepartment());
			//userBean.setAuthorities(authSet);
			userBean.setJoiningDate(localUser.getJoiningDate());
			userBean.setCreatedDate(localUser.getCreatedDate());
			userBean.setUpdateDate(localUser.getUpdateDate());
			userBean.setResponseStatus(ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			returnMap.put("User", userBean);
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getSRMUser() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<UserBean> userBeanList = new ArrayList<UserBean>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("UsersList", null);
		Department department = deptRepo.findByDepartmentName(ApplicationConstants.DepartmentConstants.SERVICE_DEPARTMENT);
		List<User> usersList = userRepo.findByDepartment(department);

		for (int i = 0; i < usersList.size(); i++) {
			UserBean userBean = new UserBean();
			userBean.setUserId(usersList.get(i).getUserId());
			userBean.setFullName(usersList.get(i).getFullName());
			userBean.setRoleName(usersList.get(i).getRole().getRolename());
			userBeanList.add(userBean);
		}

		if (userBeanList != null && userBeanList.size() > 0) {
			responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			responseMap.put("UsersList", userBeanList);
		}
		return responseMap;
	}
}
