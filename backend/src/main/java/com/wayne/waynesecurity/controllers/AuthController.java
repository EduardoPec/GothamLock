package com.wayne.waynesecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wayne.waynesecurity.dto.UserDTO;
import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.repositories.UserRepository;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
    private UserRepository userRepository;
	
	@GetMapping(value = "/me")
    public ResponseEntity<UserDTO> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado após autenticação."));
        return ResponseEntity.ok().body(new UserDTO(user));
    }
}
