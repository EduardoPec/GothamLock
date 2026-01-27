package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.model.dto.request.InventoryRequestDTO;
import com.wayne.waynesecurity.model.dto.response.InventoryResponseDTO;
import com.wayne.waynesecurity.services.InventoryService;
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
@RequestMapping(value = "/inventories")
@Tag(name = "Inventário", description = "Endpoints para gerenciamento do inventário de equipamentos, veículos e dispositivos")
public class InventoryController {

	private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os itens do inventário", description = "Retorna todos os itens cadastrados no inventário. Requer permissão ADMIN_SEGURANCA ou GERENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado, requer role ADMIN_SEGURANCA ou GERENTE")
    })
	public ResponseEntity<List<InventoryResponseDTO>> findAll() {
		List<InventoryResponseDTO> inventory = service.findAll();
		return ResponseEntity.ok().body(inventory);
	}
	
	@GetMapping(value = "/{id}")
    @Operation(summary = "Buscar item do inventário por ID", description = "Retorna um item específico do inventário pelo seu ID. Requer permissão ADMIN_SEGURANCA ou GERENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado, requer role ADMIN_SEGURANCA ou GERENTE"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
	public ResponseEntity<InventoryResponseDTO> findById(@PathVariable Long id) {
		InventoryResponseDTO responseDTO = service.findById(id);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping
    @Operation(summary = "Adicionar novo item ao inventário", description = "Cria um novo item no inventário. Requer permissão ADMIN_SEGURANCA ou GERENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado, requer role ADMIN_SEGURANCA ou GERENTE"),
    })
	public ResponseEntity<InventoryResponseDTO> insert(@Valid @RequestBody InventoryRequestDTO request) {
        InventoryResponseDTO responseDTO = service.insert(request);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
				.buildAndExpand(responseDTO.getId())
                .toUri();
		return ResponseEntity.created(uri).body(responseDTO);
	}
	
	@DeleteMapping(value = "/{id}")
    @Operation(summary = "Excluir item do inventário", description = "Remove um item do inventário. Requer permissão ADMIN_SEGURANCA ou GERENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item excluido com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado, requer role ADMIN_SEGURANCA ou GERENTE"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito, item possui registros dependentes")
    })
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar item do inventário", description = "Atualiza os dados de um item existente no inventário. Requer permissão ADMIN_SEGURANCA ou GERENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado, requer role ADMIN_SEGURANCA ou GERENTE"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
	public ResponseEntity<InventoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody InventoryRequestDTO request) {
        InventoryResponseDTO responseDTO = service.update(id, request);
		return ResponseEntity.ok().body(responseDTO);
	}
}
