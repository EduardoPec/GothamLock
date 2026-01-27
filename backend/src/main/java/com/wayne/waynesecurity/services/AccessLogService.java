package com.wayne.waynesecurity.services;

import com.wayne.waynesecurity.mapper.Imapper;
import com.wayne.waynesecurity.model.AccessLog;
import com.wayne.waynesecurity.model.dto.request.AccessLogRequestDTO;
import com.wayne.waynesecurity.model.dto.response.AccessLogResponseDTO;
import com.wayne.waynesecurity.repositories.AccessLogRepository;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccessLogService {

	private final AccessLogRepository repository;
    private final Imapper mapper;

    public AccessLogService(AccessLogRepository repository, Imapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AccessLogResponseDTO> findAll() {
		return repository.findAll().stream()
                .map(mapper::toAccessLogResponse)
                .collect(Collectors.toList());
	}
	
	public AccessLogResponseDTO findById(Long id) {
		AccessLog accessLog = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toAccessLogResponse(accessLog);
	}
}
