package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.model.dto.InventoryRequestDTO;
import com.wayne.waynesecurity.model.dto.InventoryResponseDTO;
import com.wayne.waynesecurity.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/inventories")
public class InventoryController {

	private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping
	public ResponseEntity<List<InventoryResponseDTO>> findAll() {
		List<Inventory> inventory = service.findAll();
        List<InventoryResponseDTO> response = inventory.stream()
                .map(InventoryResponseDTO::fromEntity)
                .toList();
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<InventoryResponseDTO> findById(@PathVariable Long id) {
		Inventory inventory = service.findById(id);
        InventoryResponseDTO response = InventoryResponseDTO.fromEntity(inventory);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<InventoryResponseDTO> insert(@Valid @RequestBody InventoryRequestDTO request) {
		Inventory inventory = request.toEntity();
        Inventory savedInventory = service.insert(inventory);
        InventoryResponseDTO response = InventoryResponseDTO.fromEntity(savedInventory);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
				.buildAndExpand(savedInventory.getId())
                .toUri();
		return ResponseEntity.created(uri).body(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<InventoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody InventoryRequestDTO request) {
		Inventory inventory = request.toEntity();
        inventory.setId(id);
        Inventory updatedInventory = service.update(id, inventory);
        InventoryResponseDTO response = InventoryResponseDTO.fromEntity(updatedInventory);
		return ResponseEntity.ok().body(response);
	}
}
