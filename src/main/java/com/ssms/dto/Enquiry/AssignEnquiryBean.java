package com.ssms.dto.Enquiry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignEnquiryBean {
	private Long enquiryId;
	private Long assignToId;
	private String remark;
}
