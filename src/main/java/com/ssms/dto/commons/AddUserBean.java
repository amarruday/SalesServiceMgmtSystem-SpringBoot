package com.ssms.dto.commons;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserBean {
	private Long userId;
	private String fullName;
	private String employeeCode;
	private String email;
	private String mobileNumber;
	private String joiningDate;
	private String profilePic;
	private Long roleId;
	private Long managerId;
	private Long productCatagoryId;
	private Long userProductCatagoryId;
	private boolean status;
}
