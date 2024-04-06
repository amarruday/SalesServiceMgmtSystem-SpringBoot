package com.yashsales.dto.visit;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchVisitBean {
	private Date startDate;
	private Date endDate;
	private Long actionTypeId;
	private Long userId;
	private Long customerId;
}
