package com.ssms.dto.commons;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleWiseManagersListBean {	
	private Long managerId;
	private String manager_productCatagory;
}
