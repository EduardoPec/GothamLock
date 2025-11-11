package com.wayne.waynesecurity.services;

import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.repositories.InventoryRepository;
import com.wayne.waynesecurity.services.exceptions.DatabaseException;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

	private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<Inventory> findAll() {
		return repository.findAll();
	}

	public Inventory findById(Long id) {
		Optional<Inventory> inventory = repository.findById(id);
		return inventory.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Inventory insert(Inventory inventory) {
		return repository.save(inventory);
	}
	
	public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
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
	
	public Inventory update(Long id, Inventory inventory) {
		try {
			Inventory entity = repository.getReferenceById(id);
			updateData(entity, inventory);
			return repository.save(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Inventory entity, Inventory inventory) {
		entity.setName(inventory.getName());
		entity.setType(inventory.getType());
		entity.setStatus(inventory.getStatus());
	}
}

