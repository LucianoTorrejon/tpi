package com.tpi.ratescosts.service;

import com.tpi.ratescosts.entity.Tariff;

import java.util.List;
import java.util.Optional;

public interface TariffService {
    Tariff create(Tariff tariff);
    Tariff update(Long id, Tariff tariff);
    void delete(Long id);
    Optional<Tariff> findById(Long id);
    List<Tariff> findAll();
}
