package com.wayne.waynesecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.repositories.InventoryRepository;
import com.wayne.waynesecurity.services.exceptions.DatabaseException;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository repository;

	public List<Inventory> findAll() {
		return repository.findAll();
	}

	public Inventory findById(Long id) {
		Optional<Inventory> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Inventory insert(Inventory obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Inventory update(Long id, Inventory obj) {
		try {
			Inventory entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Inventory entity, Inventory obj) {
		entity.setName(obj.getName());
		entity.setType(obj.getType());
		entity.setStatus(obj.getStatus());
	}
}

