package com.wayne.waynesecurity.services;

import com.wayne.waynesecurity.mapper.Imapper;
import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.model.dto.request.InventoryRequestDTO;
import com.wayne.waynesecurity.model.dto.response.InventoryResponseDTO;
import com.wayne.waynesecurity.repositories.InventoryRepository;
import com.wayne.waynesecurity.services.exceptions.DatabaseException;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

	private final InventoryRepository repository;
    private final Imapper mapper;

    public InventoryService(InventoryRepository repository, Imapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<InventoryResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toInventoryResponse)
                .collect(Collectors.toList());
	}

	public InventoryResponseDTO findById(Long id) {
		Inventory inventory = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toInventoryResponse(inventory);
	}
	
	public InventoryResponseDTO insert(InventoryRequestDTO requestDTO) {
        Inventory inventory = mapper.inventoryToEntity(requestDTO);

        Inventory saveInventory = repository.save(inventory);
		return mapper.toInventoryResponse(saveInventory);
	}

    @Transactional
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

    @Transactional
	public InventoryResponseDTO update(Long id, InventoryRequestDTO requestDTO) {
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventário não encontrado: " + id));
        updateData(inventory, requestDTO);

        Inventory saveInventory = repository.save(inventory);
        return mapper.toInventoryResponse(saveInventory);
	}

	private void updateData(Inventory entity, InventoryRequestDTO requestDTO) {
		entity.setName(requestDTO.getName());
		entity.setType(requestDTO.getType());
		entity.setStatus(requestDTO.getStatus());
	}
}

