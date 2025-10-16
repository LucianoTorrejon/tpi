package com.tpi.trucksdepots.repository;

import com.tpi.trucksdepots.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TruckRepository extends JpaRepository<Truck, Long> {
    List<Truck> findByDisponibleTrue();
}
