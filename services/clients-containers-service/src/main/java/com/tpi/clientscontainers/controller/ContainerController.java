package com.tpi.clientscontainers.controller;

import com.tpi.clientscontainers.dto.AssignContainerRequest;
import com.tpi.clientscontainers.entity.ContainerEntity;
import com.tpi.clientscontainers.service.ContainerService;
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
@RequestMapping("/api/containers")
@Tag(name = "Contenedores", description = "Gestión de contenedores")
@SecurityRequirement(name = "bearer-jwt")
public class ContainerController {

    private final ContainerService containerService;

    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Crear contenedor")
    public ResponseEntity<ContainerEntity> create(@Valid @RequestBody ContainerEntity containerEntity) {
        ContainerEntity created = containerService.create(containerEntity);
        return ResponseEntity.created(URI.create("/api/containers/" + created.getId())).body(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Listar contenedores")
    public ResponseEntity<List<ContainerEntity>> findAll() {
        return ResponseEntity.ok(containerService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador','Cliente')")
    @Operation(summary = "Obtener contenedor por id")
    public ResponseEntity<ContainerEntity> findById(@PathVariable Long id) {
        return containerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAnyRole('Operador','Administrador','Cliente')")
    @Operation(summary = "Listar contenedores por cliente")
    public ResponseEntity<List<ContainerEntity>> findByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(containerService.findByCliente(clientId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Actualizar contenedor")
    public ResponseEntity<ContainerEntity> update(@PathVariable Long id, @Valid @RequestBody ContainerEntity containerEntity) {
        return ResponseEntity.ok(containerService.update(id, containerEntity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Eliminar contenedor")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        containerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/assign")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Asignar contenedor a cliente")
    public ResponseEntity<ContainerEntity> assignToClient(@Valid @RequestBody AssignContainerRequest request) {
        return ResponseEntity.ok(containerService.assignToClient(request.containerId(), request.clientId()));
    }
}
