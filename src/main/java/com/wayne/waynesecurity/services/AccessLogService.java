package com.wayne.waynesecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wayne.waynesecurity.model.AccessLog;
import com.wayne.waynesecurity.repositories.AccessLogRepository;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;

@Service
public class AccessLogService {
	
	@Autowired
	private AccessLogRepository repository;
	
	public List<AccessLog> findAll() {
		return repository.findAll();
	}
	
	public AccessLog findById(Long id) {
		Optional<AccessLog> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
