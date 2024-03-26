package com.yashsales.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String fullName;
	private String employeeCode;
	private String username;
	private String password;
	private String email;
	private String mobileNumber;
	private boolean status = true;
	private Role role;
	private User manager;	
	private Department department;
	private String joiningDate;
	private String profilePic;
	private Timestamp createdDate;
	private Timestamp updateDate;
	private Set<Authority> authorities;
		
	

	public AuthUser(User user) {
		this.userId = user.getUserId();
		this.fullName = user.getFullName();
		this.employeeCode = user.getEmployeeCode();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.mobileNumber = user.getMobileNumber();
		this.status = user.isStatus();
		this.manager = user.getManager();
		this.department = user.getDepartment();
		this.role = user.getRole();
		this.joiningDate = user.getJoiningDate();
		Set<Authority> authSet = new HashSet<>();
		authSet.add(new Authority(user.getRole().getrolename()));
		this.authorities= authSet;
		this.profilePic = user.getProfilePic();
		this.createdDate = user.getCreatedDate();
		this.updateDate = user.getUpdateDate();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.status;
	}

	//AuthUser Setters and Getters
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
