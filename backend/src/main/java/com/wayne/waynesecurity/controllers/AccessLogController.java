package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.model.AccessLog;
import com.wayne.waynesecurity.model.dto.response.AccessLogResponseDTO;
import com.wayne.waynesecurity.services.AccessLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/access-logs")
@Tag(name = "Logs de Acesso", description = "Endpoints para consulta dos logs de acesso às áreas restritas")
public class AccessLogController {

	private final AccessLogService service;

    public AccessLogController(AccessLogService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os logs de acesso", description = "Retorna todos os registros de acesso às áreas restritas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de logs de acesso retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
	public ResponseEntity<List<AccessLogResponseDTO>> findAll() {
		List<AccessLogResponseDTO> accessLogs = service.findAll();
		return ResponseEntity.ok().body(accessLogs);
	}
	
	@GetMapping(value = "/{id}")
    @Operation(summary = "Buscar log de acesso por ID", description = "Retorna um registro específico de log de acesso pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Log de acesso encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Log de acesso não encontrado")
    })
	public ResponseEntity<AccessLogResponseDTO> findById(@PathVariable Long id) {
		AccessLogResponseDTO responseDTO = service.findById(id);
		return ResponseEntity.ok().body(responseDTO);
	}
}
