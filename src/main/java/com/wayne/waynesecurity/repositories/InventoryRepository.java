package com.wayne.waynesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wayne.waynesecurity.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}