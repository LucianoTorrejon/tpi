package com.tpi.trucksdepots.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CapacityValidationRequest(
        @NotNull Long truckId,
        @Positive Double peso,
        @Positive Double volumen
) {
}
