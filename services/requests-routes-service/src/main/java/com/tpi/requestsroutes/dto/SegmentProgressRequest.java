package com.tpi.requestsroutes.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record SegmentProgressRequest(
        @NotNull OffsetDateTime fechaHora,
        Double costoReal,
        String observacion,
        boolean finalizar
) {
}
