package com.ssms.dto.commons;

import java.sql.Timestamp;
import java.util.Set;

import com.ssms.entity.Authority;
import com.ssms.entity.Department;
import com.ssms.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserBean extends BaseResponse {
	private Long userId;
	private String fullName;
	private String employeeCode;
	private String username;
	private String password;
	private String email;
	private String mobileNumber;
	private boolean status = true;
	private Long managerId;
	private Long departmentId;
	private Long roleId;
	private String roleName;
	private User manager;
	private String managerName;
	private Department department;
	private String departmentName;
	private String joiningDate;
	private String profilePic;
	private Set<Authority> authorities;
	private Timestamp createdDate;
	private Timestamp updateDate;
	private Long productCatagoryId;
	private String productCatagoryName;
}
