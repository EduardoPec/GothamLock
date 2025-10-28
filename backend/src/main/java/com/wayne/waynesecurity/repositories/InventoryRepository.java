package com.wayne.waynesecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wayne.waynesecurity.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	@Query("SELECT obj.status, COUNT(obj) FROM Inventory obj GROUP BY obj.status")
    List<Object[]> countByStatus();
}