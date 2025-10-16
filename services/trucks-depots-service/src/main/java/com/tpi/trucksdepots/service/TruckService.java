package com.tpi.trucksdepots.service;

import com.tpi.trucksdepots.entity.Truck;

import java.util.List;
import java.util.Optional;

public interface TruckService {
    Truck create(Truck truck);
    Truck update(Long id, Truck truck);
    void delete(Long id);
    List<Truck> findAll();
    List<Truck> findAvailable();
    Optional<Truck> findById(Long id);
    boolean validateCapacity(Long truckId, double peso, double volumen);
}
