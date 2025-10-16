package com.tpi.ratescosts.dto;

public record TruckDto(Long id,
                       String dominio,
                       Double costoKm,
                       Double consumoCombustible,
                       Double capacidadPeso,
                       Double capacidadVolumen) {
}
