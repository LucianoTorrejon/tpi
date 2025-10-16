package com.tpi.requestsroutes.dto;

import com.tpi.requestsroutes.entity.RequestStatus;

import java.time.OffsetDateTime;

public record TransportStatusResponse(RequestStatus estado, OffsetDateTime fechaHora, String descripcion) {
}
