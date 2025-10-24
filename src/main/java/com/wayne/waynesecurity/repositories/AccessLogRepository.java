package com.wayne.waynesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wayne.waynesecurity.model.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

}