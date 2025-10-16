package com.tpi.ratescosts.controller;

import com.tpi.ratescosts.dto.CostCalculationRequest;
import com.tpi.ratescosts.dto.CostCalculationResponse;
import com.tpi.ratescosts.service.CostCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/costs")
@Tag(name = "Costos", description = "Cálculo de costos de transporte")
@SecurityRequirement(name = "bearer-jwt")
public class CostController {

    private final CostCalculationService costCalculationService;

    public CostController(CostCalculationService costCalculationService) {
        this.costCalculationService = costCalculationService;
    }

    @PostMapping("/estimate")
    @PreAuthorize("hasAnyRole('Operador','Administrador','Cliente')")
    @Operation(summary = "Calcular costo estimado de un traslado")
    public ResponseEntity<CostCalculationResponse> calculate(@Valid @RequestBody CostCalculationRequest request) {
        return ResponseEntity.ok(costCalculationService.calculateCost(request));
    }
}
