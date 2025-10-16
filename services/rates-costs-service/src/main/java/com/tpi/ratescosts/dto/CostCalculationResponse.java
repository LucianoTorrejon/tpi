package com.tpi.ratescosts.dto;

import java.util.List;

public record CostCalculationResponse(double distanciaKm,
                                      double tiempoHorasEstimado,
                                      double costoTotalEstimado,
                                      List<TruckCostDetail> detallesCamion) {

    public record TruckCostDetail(Long camionId,
                                  double costoKm,
                                  double costoCombustible,
                                  boolean capacidadValida) {
    }
}
