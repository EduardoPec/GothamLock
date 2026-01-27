package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.mapper.MapperImplementacao;
import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.dto.request.UserRequestDTO;
import com.wayne.waynesecurity.model.dto.response.UserResponseDTO;
import com.wayne.waynesecurity.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários do sistema")
public class UserController {

	private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna todos os usuários cadastrados no sistema. Requer permissão ADMIN_SEGURANCA")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado, requer role ADMIN_SEGURANCA")
    })
	public ResponseEntity<List<UserResponseDTO>> findAll() {
		List<UserResponseDTO> users = service.findAll();
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping(value = "/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo seu ID. Requer permissão ADMIN_SEGURANCA")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado, requer role ADMIN_SEGURANCA"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
		UserResponseDTO responseDTO = service.findById(id);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema. Requer permissão ADMIN_SEGURANCA")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado, requer role ADMIN_SEGURANCA")
    })
	public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO responseDTO = service.insert(request);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
				.buildAndExpand(responseDTO.getId())
                .toUri();
		return ResponseEntity.created(uri).body(responseDTO);
	}
	
	@DeleteMapping(value = "/{id}")
    @Operation(summary = "Excluir usuário", description = "Remove um usuário do sistema. Requer permissão ADMIN_SEGURANCA")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado, requer role ADMIN_SEGURANCA"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito, usuário possui registros dependentes")
    })
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente. Requer permissão ADMIN_SEGURANCA")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado, requer role ADMIN_SEGURANCA"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
	public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO request) {
		UserResponseDTO responseDTO = service.update(id, request);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping(value = "/{id}/access-area/{area}")
    @Operation(summary = "Registrar acesso a área", description = "Simula o acesso de um usuário a uma área específica e verifica se tem permissão")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Acesso autorizado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado à área"),
            @ApiResponse(responseCode = "404", description = "Usuário ou área não encontrada")
    })
    public ResponseEntity<Void> enterArea(@PathVariable Long id, @PathVariable String area) {
        service.enterArea(id, area); 
        return ResponseEntity.ok().build();
    }
}
