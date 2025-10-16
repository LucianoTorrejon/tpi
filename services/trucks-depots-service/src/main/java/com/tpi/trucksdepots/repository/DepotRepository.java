package com.tpi.trucksdepots.repository;

import com.tpi.trucksdepots.entity.Depot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepotRepository extends JpaRepository<Depot, Long> {
}
