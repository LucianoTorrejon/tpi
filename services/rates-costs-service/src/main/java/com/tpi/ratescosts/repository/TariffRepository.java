package com.tpi.ratescosts.repository;

import com.tpi.ratescosts.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
}
