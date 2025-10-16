package com.tpi.requestsroutes.service;

import com.tpi.requestsroutes.dto.AssignRouteRequest;
import com.tpi.requestsroutes.dto.CreateTransportRequest;
import com.tpi.requestsroutes.dto.SegmentProgressRequest;
import com.tpi.requestsroutes.dto.TransportStatusResponse;
import com.tpi.requestsroutes.entity.RouteSegment;
import com.tpi.requestsroutes.entity.TransportRequest;

import java.util.List;
import java.util.Optional;

public interface TransportRequestService {
    TransportRequest createRequest(CreateTransportRequest request);
    TransportRequest updateStatus(Long requestId, String status, String descripcion);
    List<TransportStatusResponse> trackContainer(Long containerId);
    TransportRequest assignRoute(Long requestId, AssignRouteRequest routeRequest);
    RouteSegment registerSegmentProgress(Long segmentId, SegmentProgressRequest progressRequest);
    Optional<TransportRequest> findById(Long id);
    List<TransportRequest> findAll();
}
