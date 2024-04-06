package com.yashsales.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yashsales.dto.commons.DatewiseSearchBean;
@Service
public interface ReportService {
	public abstract Map<String, Object> getTicketTypeWiseTicketReport(DatewiseSearchBean datewiseSearchBean);
	public abstract Map<String, Object> getProductWiseTicketReport(DatewiseSearchBean datewiseSearchBean);
	public abstract Map<String, Object> getEmployeewiseTicketReport(DatewiseSearchBean searchBean);
	
	public abstract Map<String, Object> getEnquirySourceEfficacyWiseEnquiryReport(DatewiseSearchBean searchBean);
	public abstract Map<String, Object> getProductwiseEnquiryReport(DatewiseSearchBean searchBean);
	public abstract Map<String, Object> getEmployeeWiseEnquiryReport(DatewiseSearchBean searchBean);

	public abstract Map<String, Object> getProductTypeWiseEnquiryChartReport();
	public abstract Map<String, Object> getProductTypeWiseTicketChartReport();
	public abstract Map<String, Object> getTicketTypeWiseTicketChartReport();
	public abstract Map<String, Object> getProductWiseTicketTypeCountReport(DatewiseSearchBean searchBean);
}
