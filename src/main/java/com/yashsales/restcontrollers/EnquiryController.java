package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.dto.Enquiry.AddEnquiryBean;
import com.yashsales.dto.Enquiry.EnquirySearchBean;
import com.yashsales.dto.Enquiry.AssignEnquiryBean;
import com.yashsales.dto.Enquiry.CancelEnquiryBean;
import com.yashsales.dto.Enquiry.ConvertToProspectBean;
import com.yashsales.dto.Enquiry.WonLostEnquiryBean;
import com.yashsales.service.EnquiryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/enquiry")
@CrossOrigin("*")
public class EnquiryController {

	private final EnquiryService enqService;

	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> addEnquiry(@RequestBody AddEnquiryBean enquiryBean) {
		return new ResponseEntity<>(enqService.addEnquiry(enquiryBean), HttpStatus.CREATED);
	}

	@PostMapping("/get")
	public ResponseEntity<Map<String, Object>> getEnquiries(@RequestBody EnquirySearchBean searchBean) {
		return new ResponseEntity<>(enqService.getPaginatedEnquiries(searchBean), HttpStatus.OK);
	}

	@GetMapping("/getassignedtolist/")
	public ResponseEntity<Map<String, Object>> getAssignedToList() {
		return new ResponseEntity<>(enqService.getAssignedToList(), HttpStatus.OK);
	}

	@GetMapping("/{enquiryId}")
	public ResponseEntity<Map<String, Object>> getEnquiryDetails(@PathVariable Long enquiryId) {
		return new ResponseEntity<>(enqService.getEnquiryDetails(enquiryId), HttpStatus.OK);
	}

	@PostMapping("/assign")
	public ResponseEntity<Map<String, Object>> assignEnquiry(@RequestBody AssignEnquiryBean assignEnquiry) {
		return new ResponseEntity<>(enqService.assignEnquiry(assignEnquiry), HttpStatus.OK);
	}

	@PostMapping("/cancel")
	public ResponseEntity<Map<String, Object>> cancelEnquiry(@RequestBody CancelEnquiryBean cancelEnquiry) {
		return new ResponseEntity<>(enqService.cancelEnquiry(cancelEnquiry), HttpStatus.OK);
	}

	@PostMapping("/convertToProspect")
	public ResponseEntity<Map<String, Object>> convertToProspectEnquiry(@RequestBody ConvertToProspectBean convertToProspect) {
		return new ResponseEntity<>(enqService.convertToProspectEnquiry(convertToProspect), HttpStatus.OK);
	}

	@PostMapping("/won")
	public ResponseEntity<Map<String, Object>> wonEnquiry(@RequestBody WonLostEnquiryBean wonEnquiry) {
		return new ResponseEntity<>(enqService.wonEnquiry(wonEnquiry), HttpStatus.OK);
	}

	@PostMapping("/lost")
	public ResponseEntity<Map<String, Object>> lostEnquiry(@RequestBody WonLostEnquiryBean lostEnquiry) {
		return new ResponseEntity<>(enqService.lostEnquiry(lostEnquiry), HttpStatus.OK);
	}

}
