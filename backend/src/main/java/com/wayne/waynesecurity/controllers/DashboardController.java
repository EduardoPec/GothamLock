package com.wayne.waynesecurity.controllers;

import com.wayne.waynesecurity.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/dashboard")
@Tag(name = "Dashboard", description = "Endpoints para métricas e relatórios do sistema")
public class DashboardController {

	private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping(value = "/inventory-status")
    @Operation(summary = "Métricas de status do inventário", description = "Retorna contagem de itens por status no inventário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Métricas retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
	public ResponseEntity<Map<String, Long>> getInventoryStatusMetrics() {
		Map<String, Long> metrics = service.getInventoryStatusMetrics();
		return ResponseEntity.ok().body(metrics);
	}
	
	@GetMapping(value = "/access-results")
    @Operation(summary = "Métricas de resultados de acesso", description = "Retorna contagem de acessos autorizados vs negados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Métricas retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
	public ResponseEntity<Map<String, Long>> getAccessResultMetrics() {
		Map<String, Long> metrics = service.getAccessResultMetrics();
		return ResponseEntity.ok().body(metrics);
	}
}
