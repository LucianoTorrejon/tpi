package com.tpi.trucksdepots.service;

import com.tpi.trucksdepots.entity.Depot;

import java.util.List;
import java.util.Optional;

public interface DepotService {
    Depot create(Depot depot);
    Depot update(Long id, Depot depot);
    void delete(Long id);
    List<Depot> findAll();
    Optional<Depot> findById(Long id);
}
