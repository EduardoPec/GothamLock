package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.model.dto.request.InventoryRequestDTO;
import com.wayne.waynesecurity.model.dto.response.InventoryResponseDTO;
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
		List<InventoryResponseDTO> inventory = service.findAll();
		return ResponseEntity.ok().body(inventory);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<InventoryResponseDTO> findById(@PathVariable Long id) {
		InventoryResponseDTO responseDTO = service.findById(id);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping
	public ResponseEntity<InventoryResponseDTO> insert(@Valid @RequestBody InventoryRequestDTO request) {
        InventoryResponseDTO responseDTO = service.insert(request);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
				.buildAndExpand(responseDTO.getId())
                .toUri();
		return ResponseEntity.created(uri).body(responseDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<InventoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody InventoryRequestDTO request) {
        InventoryResponseDTO responseDTO = service.update(id, request);
		return ResponseEntity.ok().body(responseDTO);
	}
}
