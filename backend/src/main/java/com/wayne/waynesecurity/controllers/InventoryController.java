package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.services.InventoryService;
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
	public ResponseEntity<List<Inventory>> findAll() {
		List<Inventory> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Inventory> findById(@PathVariable Long id) {
		Inventory obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Inventory> insert(@RequestBody Inventory obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Inventory> update(@PathVariable Long id, @RequestBody Inventory obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
