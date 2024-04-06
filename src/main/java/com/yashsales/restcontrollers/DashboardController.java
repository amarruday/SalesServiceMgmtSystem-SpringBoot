package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.service.DashboardService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/dashboard")
@CrossOrigin("*")
public class DashboardController {

	private final DashboardService dashboardService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getDashboardDetails() {
		return new ResponseEntity<>(dashboardService.getDashboardDetails(), HttpStatus.OK);
	}
}
