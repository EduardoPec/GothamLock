package com.wayne.waynesecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wayne.waynesecurity.model.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
	@Query("SELECT obj.result, COUNT(obj) FROM AccessLog obj GROUP BY obj.result")
    List<Object[]> countByResult();
}