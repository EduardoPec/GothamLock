package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.model.AccessLog;
import com.wayne.waynesecurity.model.dto.AccessLogResponseDTO;
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
	public ResponseEntity<List<AccessLogResponseDTO>> findAll() {
		List<AccessLog> accessLogs = service.findAll();
        List<AccessLogResponseDTO> response = accessLogs.stream()
                .map(AccessLogResponseDTO::fromEntity)
                .toList();
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AccessLogResponseDTO> findById(@PathVariable Long id) {
		AccessLog accessLog = service.findById(id);
        AccessLogResponseDTO response = AccessLogResponseDTO.fromEntity(accessLog);
		return ResponseEntity.ok().body(response);
	}
}
