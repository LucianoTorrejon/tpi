package com.tpi.requestsroutes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AssignRouteRequest(
        @NotNull @Min(1) Integer cantidadTramos,
        @NotNull @Min(0) Integer cantidadDepositos,
        @NotNull @Valid List<SegmentDefinition> tramos
) {
    public record SegmentDefinition(
            @NotNull String origen,
            @NotNull String destino,
            String tipo,
            Double costoAprox,
            Long camionId
    ) {
    }
}
