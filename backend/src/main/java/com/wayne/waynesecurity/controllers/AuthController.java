package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.mapper.Imapper;
import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.dto.response.UserResponseDTO;
import com.wayne.waynesecurity.repositories.UserRepository;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final Imapper mapper;

    public AuthController(UserRepository userRepository, Imapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResourceNotFoundException("Usuário não autenticado");
        }

        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado após autenticação: " + email));

        UserResponseDTO responseDTO = mapper.toUserResponse(user);
        return ResponseEntity.ok().body(responseDTO);
    }
}
