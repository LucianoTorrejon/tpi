package com.tpi.clientscontainers.controller;

import com.tpi.clientscontainers.entity.Client;
import com.tpi.clientscontainers.service.ClientService;
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
@RequestMapping("/api/clients")
@Tag(name = "Clientes", description = "Gestión de clientes")
@SecurityRequirement(name = "bearer-jwt")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Crear un cliente")
    public ResponseEntity<Client> create(@Valid @RequestBody Client client) {
        Client created = clientService.create(client);
        return ResponseEntity.created(URI.create("/api/clients/" + created.getId())).body(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Listar clientes")
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador','Cliente')")
    @Operation(summary = "Obtener cliente por id")
    public ResponseEntity<Client> findById(@PathVariable Long id) {
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Actualizar cliente")
    public ResponseEntity<Client> update(@PathVariable Long id, @Valid @RequestBody Client client) {
        return ResponseEntity.ok(clientService.update(id, client));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Eliminar cliente")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
