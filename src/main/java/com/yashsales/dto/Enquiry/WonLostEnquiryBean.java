package com.yashsales.dto.Enquiry;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WonLostEnquiryBean {
	private Long enquiryId;
	private String remark;
	private String machineSerialNumber;
	private Date purchaseDate;
}
