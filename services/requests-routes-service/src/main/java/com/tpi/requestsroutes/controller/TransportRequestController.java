package com.tpi.requestsroutes.controller;

import com.tpi.requestsroutes.dto.AssignRouteRequest;
import com.tpi.requestsroutes.dto.CreateTransportRequest;
import com.tpi.requestsroutes.dto.SegmentProgressRequest;
import com.tpi.requestsroutes.dto.TransportStatusResponse;
import com.tpi.requestsroutes.entity.RouteSegment;
import com.tpi.requestsroutes.entity.TransportRequest;
import com.tpi.requestsroutes.service.TransportRequestService;
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
@RequestMapping("/api/requests")
@Tag(name = "Solicitudes", description = "Gestión de solicitudes de transporte")
@SecurityRequirement(name = "bearer-jwt")
public class TransportRequestController {

    private final TransportRequestService transportRequestService;

    public TransportRequestController(TransportRequestService transportRequestService) {
        this.transportRequestService = transportRequestService;
    }

    @PostMapping
    @PreAuthorize("hasRole('Cliente')")
    @Operation(summary = "Registrar una solicitud de transporte")
    public ResponseEntity<TransportRequest> create(@Valid @RequestBody CreateTransportRequest request) {
        TransportRequest created = transportRequestService.createRequest(request);
        return ResponseEntity.created(URI.create("/api/requests/" + created.getId())).body(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Listar solicitudes")
    public ResponseEntity<List<TransportRequest>> findAll() {
        return ResponseEntity.ok(transportRequestService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Cliente','Operador','Administrador')")
    @Operation(summary = "Obtener solicitud por id")
    public ResponseEntity<TransportRequest> findById(@PathVariable Long id) {
        return transportRequestService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Actualizar el estado de una solicitud")
    public ResponseEntity<TransportRequest> updateStatus(@PathVariable Long id,
                                                         @RequestParam String status,
                                                         @RequestParam(defaultValue = "") String descripcion) {
        return ResponseEntity.ok(transportRequestService.updateStatus(id, status, descripcion));
    }

    @PostMapping("/{id}/route")
    @PreAuthorize("hasAnyRole('Operador','Administrador')")
    @Operation(summary = "Asignar ruta a una solicitud")
    public ResponseEntity<TransportRequest> assignRoute(@PathVariable Long id,
                                                        @Valid @RequestBody AssignRouteRequest routeRequest) {
        return ResponseEntity.ok(transportRequestService.assignRoute(id, routeRequest));
    }

    @PostMapping("/segments/{segmentId}/progress")
    @PreAuthorize("hasRole('Transportista')")
    @Operation(summary = "Registrar avance de un tramo")
    public ResponseEntity<RouteSegment> segmentProgress(@PathVariable Long segmentId,
                                                         @Valid @RequestBody SegmentProgressRequest progressRequest) {
        return ResponseEntity.ok(transportRequestService.registerSegmentProgress(segmentId, progressRequest));
    }

    @GetMapping("/container/{containerId}/tracking")
    @PreAuthorize("hasAnyRole('Cliente','Operador','Administrador')")
    @Operation(summary = "Consultar estado de un contenedor")
    public ResponseEntity<List<TransportStatusResponse>> trackContainer(@PathVariable Long containerId) {
        return ResponseEntity.ok(transportRequestService.trackContainer(containerId));
    }
}
