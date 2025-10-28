package com.wayne.waynesecurity.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wayne.waynesecurity.services.DashboardService;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {
	
	@Autowired
	private DashboardService service;
	
	@GetMapping(value = "/inventory-status")
	public ResponseEntity<Map<String, Long>> getInventoryStatusMetrics() {
		Map<String, Long> metrics = service.getInventoryStatusMetrics();
		return ResponseEntity.ok().body(metrics);
	}
	
	@GetMapping(value = "/access-results")
	public ResponseEntity<Map<String, Long>> getAccessResultMetrics() {
		Map<String, Long> metrics = service.getAccessResultMetrics();
		return ResponseEntity.ok().body(metrics);
	}
}
