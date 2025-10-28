package com.wayne.waynesecurity.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wayne.waynesecurity.model.enums.AccessResult;
import com.wayne.waynesecurity.model.enums.InventoryStatus;
import com.wayne.waynesecurity.repositories.AccessLogRepository;
import com.wayne.waynesecurity.repositories.InventoryRepository;

@Service
public class DashboardService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private AccessLogRepository accessLogRepository;
	
	public Map<String, Long> getInventoryStatusMetrics() {
		List<Object[]> results = inventoryRepository.countByStatus();
		
		Map<String, Long> metrics = new HashMap<>();
        for (Object[] result : results) {
            metrics.put(((InventoryStatus) result[0]).toString(), (Long) result[1]);
        }
        return metrics;
	}
	
	public Map<String, Long> getAccessResultMetrics() {
        List<Object[]> results = accessLogRepository.countByResult();

        Map<String, Long> metrics = new HashMap<>();
        for (Object[] result : results) {
            metrics.put(((AccessResult) result[0]).toString(), (Long) result[1]);
        }
        return metrics;
    }
}
