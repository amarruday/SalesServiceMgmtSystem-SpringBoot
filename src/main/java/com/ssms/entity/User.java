package com.ssms.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "userId")
	private User manager;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departmentId")
	private Department department;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roleId")
	private Role role;
	private String fullName;
	private String employeeCode;
	private String username;
	private String password;
	private String email;
	private String mobileNumber;
	private boolean status = true;
	private String joiningDate;
	private String profilePic;
	
	@CreationTimestamp
	private Timestamp createdDate;
	
	@CreatedBy
	private String createdBy;
	
	@UpdateTimestamp
	private Timestamp updateDate;
	
	private String updatedBy;
}
