package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.mapper.Imapper;
import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.dto.response.UserResponseDTO;
import com.wayne.waynesecurity.repositories.UserRepository;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "Autenticação", description = "Endpoints relacionados à autenticação de usuários")
public class AuthController {

    private final UserRepository userRepository;
    private final Imapper mapper;

    public AuthController(UserRepository userRepository, Imapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @GetMapping(value = "/me")
    @Operation(summary = "Obter usuário autenticado", description = "Retorna os dados do usuário atualmente autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário autenticado encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
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
