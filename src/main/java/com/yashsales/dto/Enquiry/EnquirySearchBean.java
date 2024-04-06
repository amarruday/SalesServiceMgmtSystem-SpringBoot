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
public class EnquirySearchBean {
	private int pageNumber;
	private int pageSize;
	private Date startDate;
	private Date endDate;
	private Long enquiryTypeId;
	private Long enquirySourceId;
	private String status;
	private Long customerId;
	private Long addedBy;
}
