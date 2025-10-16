package com.tpi.requestsroutes.service.impl;

import com.tpi.requestsroutes.dto.AssignRouteRequest;
import com.tpi.requestsroutes.dto.CreateTransportRequest;
import com.tpi.requestsroutes.dto.SegmentProgressRequest;
import com.tpi.requestsroutes.dto.TransportStatusResponse;
import com.tpi.requestsroutes.entity.*;
import com.tpi.requestsroutes.repository.*;
import com.tpi.requestsroutes.service.TransportRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransportRequestServiceImpl implements TransportRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportRequestServiceImpl.class);
    private final TransportRequestRepository requestRepository;
    private final RoutePlanRepository routePlanRepository;
    private final RouteSegmentRepository routeSegmentRepository;
    private final TransportStatusEventRepository statusEventRepository;

    public TransportRequestServiceImpl(TransportRequestRepository requestRepository,
                                       RoutePlanRepository routePlanRepository,
                                       RouteSegmentRepository routeSegmentRepository,
                                       TransportStatusEventRepository statusEventRepository) {
        this.requestRepository = requestRepository;
        this.routePlanRepository = routePlanRepository;
        this.routeSegmentRepository = routeSegmentRepository;
        this.statusEventRepository = statusEventRepository;
    }

    @Override
    public TransportRequest createRequest(CreateTransportRequest request) {
        LOGGER.info("Registering transport request {}", request.numero());
        TransportRequest transportRequest = new TransportRequest();
        transportRequest.setNumero(request.numero());
        transportRequest.setContenedorId(request.contenedorId());
        transportRequest.setClienteId(request.clienteId());
        transportRequest.setCostoEstimado(request.costoEstimado());
        transportRequest.setTiempoEstimado(request.tiempoEstimado());
        transportRequest.setEstado(RequestStatus.BORRADOR);

        TransportRequest saved = requestRepository.save(transportRequest);
        registerStatusEvent(saved, RequestStatus.BORRADOR, "Solicitud creada");
        return saved;
    }

    @Override
    public TransportRequest updateStatus(Long requestId, String status, String descripcion) {
        TransportRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        RequestStatus newStatus = RequestStatus.valueOf(status.toUpperCase());
        request.setEstado(newStatus);
        TransportRequest saved = requestRepository.save(request);
        registerStatusEvent(saved, newStatus, descripcion);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransportStatusResponse> trackContainer(Long containerId) {
        Optional<TransportRequest> request = requestRepository.findFirstByContenedorIdOrderByIdDesc(containerId);
        if (request.isEmpty()) {
            return List.of();
        }
        List<TransportStatusEvent> events = statusEventRepository
                .findBySolicitudIdOrderByFechaHoraAsc(request.get().getId());
        return events.stream()
                .sorted(Comparator.comparing(TransportStatusEvent::getFechaHora))
                .map(event -> new TransportStatusResponse(event.getEstado(), event.getFechaHora(), event.getDescripcion()))
                .toList();
    }

    @Override
    public TransportRequest assignRoute(Long requestId, AssignRouteRequest routeRequest) {
        TransportRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        RoutePlan routePlan = request.getRuta();
        if (routePlan == null) {
            routePlan = new RoutePlan();
            routePlan.setSolicitud(request);
        }
        routePlan.setCantidadTramos(routeRequest.cantidadTramos());
        routePlan.setCantidadDepositos(routeRequest.cantidadDepositos());

        List<RouteSegment> segments = new ArrayList<>();
        for (AssignRouteRequest.SegmentDefinition definition : routeRequest.tramos()) {
            RouteSegment segment = new RouteSegment();
            segment.setRuta(routePlan);
            segment.setOrigen(definition.origen());
            segment.setDestino(definition.destino());
            segment.setTipo(definition.tipo());
            segment.setCostoAprox(definition.costoAprox());
            segment.setCamionId(definition.camionId());
            segments.add(segment);
        }
        routePlan.getTramos().clear();
        routePlan.getTramos().addAll(segments);

        RoutePlan saved = routePlanRepository.save(routePlan);
        request.setRuta(saved);
        registerStatusEvent(request, RequestStatus.PROGRAMADA, "Ruta asignada");
        request.setEstado(RequestStatus.PROGRAMADA);
        return requestRepository.save(request);
    }

    @Override
    public RouteSegment registerSegmentProgress(Long segmentId, SegmentProgressRequest progressRequest) {
        RouteSegment segment = routeSegmentRepository.findById(segmentId)
                .orElseThrow(() -> new IllegalArgumentException("Tramo no encontrado"));
        if (progressRequest.finalizar()) {
            segment.setEstado(SegmentStatus.COMPLETADO);
            segment.setFechaHoraFin(progressRequest.fechaHora());
            segment.setCostoReal(progressRequest.costoReal());
            registerStatusEvent(segment.getRuta().getSolicitud(), RequestStatus.EN_TRANSITO,
                    "Tramo finalizado: " + segment.getDestino());
        } else {
            segment.setEstado(SegmentStatus.EN_CURSO);
            segment.setFechaHoraInicio(progressRequest.fechaHora());
            registerStatusEvent(segment.getRuta().getSolicitud(), RequestStatus.EN_TRANSITO,
                    "Tramo iniciado: " + segment.getOrigen());
        }
        return routeSegmentRepository.save(segment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransportRequest> findById(Long id) {
        return requestRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransportRequest> findAll() {
        return requestRepository.findAll();
    }

    private void registerStatusEvent(TransportRequest request, RequestStatus status, String descripcion) {
        TransportStatusEvent event = new TransportStatusEvent(request, status, OffsetDateTime.now(), descripcion);
        statusEventRepository.save(event);
    }
}
