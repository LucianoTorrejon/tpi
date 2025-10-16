package com.tpi.trucksdepots.controller;

import com.tpi.trucksdepots.entity.Depot;
import com.tpi.trucksdepots.service.DepotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/depots")
@Tag(name = "Depósitos", description = "Gestión de depósitos")
@SecurityRequirement(name = "bearer-jwt")
public class DepotController {

    private final DepotService depotService;

    public DepotController(DepotService depotService) {
        this.depotService = depotService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Registrar depósito")
    public ResponseEntity<Depot> create(@Valid @RequestBody Depot depot) {
        Depot created = depotService.create(depot);
        return ResponseEntity.created(URI.create("/api/depots/" + created.getId())).body(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador','Transportista')")
    @Operation(summary = "Listar depósitos")
    public ResponseEntity<List<Depot>> findAll() {
        return ResponseEntity.ok(depotService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Obtener depósito por id")
    public ResponseEntity<Depot> findById(@PathVariable Long id) {
        return depotService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Actualizar depósito")
    public ResponseEntity<Depot> update(@PathVariable Long id, @Valid @RequestBody Depot depot) {
        return ResponseEntity.ok(depotService.update(id, depot));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Eliminar depósito")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        depotService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
