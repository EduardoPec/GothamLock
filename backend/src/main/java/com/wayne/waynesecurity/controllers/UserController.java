package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.mapper.MapperImplementacao;
import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.dto.request.UserRequestDTO;
import com.wayne.waynesecurity.model.dto.response.UserResponseDTO;
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
		List<UserResponseDTO> users = service.findAll();
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
		UserResponseDTO responseDTO = service.findById(id);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO responseDTO = service.insert(request);

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
	public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO request) {
		UserResponseDTO responseDTO = service.update(id, request);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping(value = "/{id}/access-area/{area}")
    public ResponseEntity<Void> enterArea(@PathVariable Long id, @PathVariable String area) {
        service.enterArea(id, area); 
        return ResponseEntity.ok().build();
    }
}
