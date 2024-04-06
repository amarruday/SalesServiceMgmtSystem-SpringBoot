package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.dto.visit.SearchVisitBean;
import com.yashsales.dto.visit.VisitBean;
import com.yashsales.service.VisitService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/visit")
@CrossOrigin("*")
public class ColdVisitController {

	// Construction Injection - Lombok will create All args constructor
	private final VisitService visitService;

	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> addVisit(@RequestBody VisitBean visitBean) {
		return new ResponseEntity<>(visitService.addNewVisit(visitBean), HttpStatus.OK);
	}

	@GetMapping("/{visitId}")
	public ResponseEntity<Map<String, Object>> getVisitDetails(@PathVariable Long visitId) {
		return new ResponseEntity<>(visitService.getVisitDetails(visitId), HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllVisitDetails() {
		return new ResponseEntity<>(visitService.getAllVisits(), HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<Map<String, Object>> searchVisits(@RequestBody SearchVisitBean searchVisitBean) {
		return new ResponseEntity<>(visitService.searchVisits(searchVisitBean), HttpStatus.CREATED);
	}
}
