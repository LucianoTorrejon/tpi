package com.tpi.requestsroutes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTransportRequest(
        @NotBlank String numero,
        @NotNull Long contenedorId,
        @NotNull Long clienteId,
        @Positive Double costoEstimado,
        @Positive Double tiempoEstimado
) {
}
