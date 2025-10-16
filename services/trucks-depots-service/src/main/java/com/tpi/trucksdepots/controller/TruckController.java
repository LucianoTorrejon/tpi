package com.tpi.trucksdepots.controller;

import com.tpi.trucksdepots.dto.CapacityValidationRequest;
import com.tpi.trucksdepots.entity.Truck;
import com.tpi.trucksdepots.service.TruckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trucks")
@Tag(name = "Camiones", description = "Gestión de camiones")
@SecurityRequirement(name = "bearer-jwt")
public class TruckController {

    private final TruckService truckService;

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Registrar camión")
    public ResponseEntity<Truck> create(@Valid @RequestBody Truck truck) {
        Truck created = truckService.create(truck);
        return ResponseEntity.created(URI.create("/api/trucks/" + created.getId())).body(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Listar camiones")
    public ResponseEntity<List<Truck>> findAll() {
        return ResponseEntity.ok(truckService.findAll());
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('Operador','Administrador','Transportista')")
    @Operation(summary = "Listar camiones disponibles")
    public ResponseEntity<List<Truck>> findAvailable() {
        return ResponseEntity.ok(truckService.findAvailable());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Obtener camión por id")
    public ResponseEntity<Truck> findById(@PathVariable Long id) {
        return truckService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Actualizar camión")
    public ResponseEntity<Truck> update(@PathVariable Long id, @Valid @RequestBody Truck truck) {
        return ResponseEntity.ok(truckService.update(id, truck));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Eliminar camión")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        truckService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate-capacity")
    @PreAuthorize("hasAnyRole('Operador','Administrador','Transportista')")
    @Operation(summary = "Validar capacidad de camión para un contenedor")
    public ResponseEntity<Map<String, Object>> validateCapacity(@Valid @RequestBody CapacityValidationRequest request) {
        boolean valid = truckService.validateCapacity(request.truckId(), request.peso(), request.volumen());
        return ResponseEntity.ok(Map.of(
                "truckId", request.truckId(),
                "peso", request.peso(),
                "volumen", request.volumen(),
                "valido", valid));
    }
}
