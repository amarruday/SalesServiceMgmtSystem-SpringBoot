package com.ssms.dto.commons;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatewiseSearchBean {
	private Date startDate;
	private Date endDate;
}
