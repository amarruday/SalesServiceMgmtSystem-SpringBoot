package com.ssms.restcontrollers;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssms.dto.commons.DatewiseSearchBean;
import com.ssms.service.ReportService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/reports")
@CrossOrigin("*")
public class ReportController {
		
	private final ReportService reportService;
		
	//Ticket Reports
	@PostMapping("/tickettypewiseticketreport")
	public  Map<String, Object> getTicketTypeWiseTicketReport(@RequestBody DatewiseSearchBean datewiseSearchBean) {
		return reportService.getTicketTypeWiseTicketReport(datewiseSearchBean);
	}
	
	@PostMapping("/productwiseticketreport")
	public  Map<String, Object> getProductwiseTicketReport(@RequestBody DatewiseSearchBean datewiseSearchBean) {
		return reportService.getProductWiseTicketReport(datewiseSearchBean);
	}
	
	@PostMapping("/employeewiseticketreport")
	public  Map<String, Object> getEmployeewiseTicketReport(@RequestBody DatewiseSearchBean datewiseSearchBean) {
		return reportService.getEmployeewiseTicketReport(datewiseSearchBean);
	}
	
	@PostMapping("/productwisetickettypecountreport")
	public Map<String, Object> getProductWiseTicketTypeCountReport(@RequestBody DatewiseSearchBean searchBean ) {
		return reportService.getProductWiseTicketTypeCountReport(searchBean);
	}
		
	//EnquiryReports	
	@PostMapping("/enquirySourceEfficacyWiseEnquiryReport")
	public  Map<String, Object> getEnquirySourceEfficacyWiseEnquiryReport(@RequestBody DatewiseSearchBean datewiseSearchBean) {
		return reportService.getEnquirySourceEfficacyWiseEnquiryReport(datewiseSearchBean);
	}
	
	@PostMapping("/productwiseenquiryreport")
	public  Map<String, Object> getProductWiseEnquiryReport(@RequestBody DatewiseSearchBean datewiseSearchBean) {
		return reportService.getProductwiseEnquiryReport(datewiseSearchBean);
	}
		
	@PostMapping("/employeewiseenquiryreport")
	public  Map<String, Object> getEmployeeWiseEnquiryReport(@RequestBody DatewiseSearchBean datewiseSearchBean) {
		return reportService.getEmployeeWiseEnquiryReport(datewiseSearchBean);
	}
	
	//Charts
	@GetMapping("/producttypewiseEnquiryChartReport")
	public  Map<String, Object> getProductTypeWiseEnquiryChartReport() {
		return reportService.getProductTypeWiseEnquiryChartReport();
	}
	
	@GetMapping("/producttypewiseTicketChartReport")
	public  Map<String, Object> getProductTypeWiseTicketChartReport() {
		return reportService.getProductTypeWiseTicketChartReport();
	}
	
	@GetMapping("/tickettypewiseTicketChartReport")
	public  Map<String, Object> getTicketTypeWiseTicketChartReport() {
		return reportService.getTicketTypeWiseTicketChartReport();
	}
}