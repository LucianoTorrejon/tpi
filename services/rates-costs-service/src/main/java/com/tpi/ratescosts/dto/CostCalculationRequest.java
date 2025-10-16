package com.tpi.ratescosts.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CostCalculationRequest(
        @NotNull Double origenLatitud,
        @NotNull Double origenLongitud,
        @NotNull Double destinoLatitud,
        @NotNull Double destinoLongitud,
        @NotEmpty List<Long> camiones,
        @NotNull Double pesoContenedor,
        @NotNull Double volumenContenedor,
        @Min(0) int diasEstadia
) {
}
