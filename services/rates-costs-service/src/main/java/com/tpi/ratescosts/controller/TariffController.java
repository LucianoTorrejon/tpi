package com.tpi.ratescosts.controller;

import com.tpi.ratescosts.entity.Tariff;
import com.tpi.ratescosts.service.TariffService;
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
@RequestMapping("/api/tariffs")
@Tag(name = "Tarifas", description = "Configuración de tarifas")
@SecurityRequirement(name = "bearer-jwt")
public class TariffController {

    private final TariffService tariffService;

    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Crear tarifa")
    public ResponseEntity<Tariff> create(@Valid @RequestBody Tariff tariff) {
        Tariff created = tariffService.create(tariff);
        return ResponseEntity.created(URI.create("/api/tariffs/" + created.getId())).body(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Listar tarifas")
    public ResponseEntity<List<Tariff>> findAll() {
        return ResponseEntity.ok(tariffService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Obtener tarifa por id")
    public ResponseEntity<Tariff> findById(@PathVariable Long id) {
        return tariffService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Actualizar tarifa")
    public ResponseEntity<Tariff> update(@PathVariable Long id, @Valid @RequestBody Tariff tariff) {
        return ResponseEntity.ok(tariffService.update(id, tariff));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Eliminar tarifa")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tariffService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
