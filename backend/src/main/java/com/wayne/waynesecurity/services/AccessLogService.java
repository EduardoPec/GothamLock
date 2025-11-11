package com.wayne.waynesecurity.services;

import com.wayne.waynesecurity.model.AccessLog;
import com.wayne.waynesecurity.repositories.AccessLogRepository;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessLogService {

	private final AccessLogRepository repository;

    public AccessLogService(AccessLogRepository repository) {
        this.repository = repository;
    }

    public List<AccessLog> findAll() {
		return repository.findAll();
	}
	
	public AccessLog findById(Long id) {
		Optional<AccessLog> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
