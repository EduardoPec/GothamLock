package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.model.AccessLog;
import com.wayne.waynesecurity.services.AccessLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/access-logs")
public class AccessLogController {

	private final AccessLogService service;

    public AccessLogController(AccessLogService service) {
        this.service = service;
    }

    @GetMapping
	public ResponseEntity<List<AccessLog>> findAll() {
		List<AccessLog> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AccessLog> findById(@PathVariable Long id) {
		AccessLog obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
