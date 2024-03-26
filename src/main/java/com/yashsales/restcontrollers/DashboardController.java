package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.service.DashboardService;

@RestController
@RequestMapping("/dashboards")
@CrossOrigin("*")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getDashboardDetails() {
		return new ResponseEntity<>(dashboardService.getDashboardDetails(), HttpStatus.OK);
	}
}
