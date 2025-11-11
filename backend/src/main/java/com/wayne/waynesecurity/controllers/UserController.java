package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.dto.UserRequestDTO;
import com.wayne.waynesecurity.model.dto.UserResponseDTO;
import com.wayne.waynesecurity.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
	public ResponseEntity<List<UserResponseDTO>> findAll() {
		List<User> users = service.findAll();
        List<UserResponseDTO> response = users.stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
		User obj = service.findById(id);
        UserResponseDTO response = UserResponseDTO.fromEntity(obj);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserRequestDTO request) {
        User user = request.toEntity();
        User savedUser = service.insert(user);
        UserResponseDTO response = UserResponseDTO.fromEntity(savedUser);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
				.buildAndExpand(savedUser.getId())
                .toUri();
		return ResponseEntity.created(uri).body(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO request) {
        User obj = request.toEntity();
		User updateUser = service.update(id, obj);
        UserResponseDTO response = UserResponseDTO.fromEntity(updateUser);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/{id}/access-area/{area}")
    public ResponseEntity<Void> enterArea(@PathVariable Long id, @PathVariable String area) {
        service.enterArea(id, area); 
        return ResponseEntity.ok().build();
    }
}
