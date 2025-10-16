package com.tpi.ratescosts.service.impl;

import com.tpi.ratescosts.client.TruckServiceClient;
import com.tpi.ratescosts.dto.CostCalculationRequest;
import com.tpi.ratescosts.dto.CostCalculationResponse;
import com.tpi.ratescosts.dto.TruckDto;
import com.tpi.ratescosts.entity.Tariff;
import com.tpi.ratescosts.repository.TariffRepository;
import com.tpi.ratescosts.service.CostCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CostCalculationServiceImpl implements CostCalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CostCalculationServiceImpl.class);
    private final TariffRepository tariffRepository;
    private final TruckServiceClient truckServiceClient;
    private final GoogleMapsClient googleMapsClient;

    public CostCalculationServiceImpl(TariffRepository tariffRepository,
                                      TruckServiceClient truckServiceClient,
                                      GoogleMapsClient googleMapsClient) {
        this.tariffRepository = tariffRepository;
        this.truckServiceClient = truckServiceClient;
        this.googleMapsClient = googleMapsClient;
    }

    @Override
    public CostCalculationResponse calculateCost(CostCalculationRequest request) {
        Tariff tariff = tariffRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Configurar una tarifa antes de calcular costos"));

        GoogleMapsClient.RouteMetrics metrics = googleMapsClient.retrieveMetrics(
                request.origenLatitud(),
                request.origenLongitud(),
                request.destinoLatitud(),
                request.destinoLongitud());

        double distanceKm = metrics.distanceKm();
        double durationHours = metrics.durationHours();

        double managementCost = tariff.getCostoKmBase() * distanceKm;
        double stayCost = tariff.getCostoEstadiaDiaria() * request.diasEstadia();

        List<CostCalculationResponse.TruckCostDetail> details = new ArrayList<>();
        double totalKmCost = 0;
        double totalFuelCost = 0;

        for (Long truckId : request.camiones()) {
            TruckDto truck = truckServiceClient.getTruck(truckId);
            boolean capacityValid = request.pesoContenedor() <= truck.capacidadPeso()
                    && request.volumenContenedor() <= truck.capacidadVolumen();
            if (!capacityValid) {
                LOGGER.warn("Truck {} does not meet capacity requirements", truckId);
            }
            double kmCost = truck.costoKm() != null ? truck.costoKm() * distanceKm : 0;
            double fuelCost = truck.consumoCombustible() != null
                    ? truck.consumoCombustible() * distanceKm * tariff.getValorLitroCombustible()
                    : 0;
            totalKmCost += kmCost;
            totalFuelCost += fuelCost;
            details.add(new CostCalculationResponse.TruckCostDetail(truckId, kmCost, fuelCost, capacityValid));
        }

        double total = managementCost + stayCost + totalKmCost + totalFuelCost;
        return new CostCalculationResponse(distanceKm, durationHours, total, details);
    }
}
