package com.tpi.ratescosts.service.impl;

import com.tpi.ratescosts.entity.Tariff;
import com.tpi.ratescosts.repository.TariffRepository;
import com.tpi.ratescosts.service.TariffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TariffServiceImpl implements TariffService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TariffServiceImpl.class);
    private final TariffRepository tariffRepository;

    public TariffServiceImpl(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    @Override
    public Tariff create(Tariff tariff) {
        LOGGER.info("Creating tariff");
        return tariffRepository.save(tariff);
    }

    @Override
    public Tariff update(Long id, Tariff tariff) {
        return tariffRepository.findById(id)
                .map(existing -> {
                    existing.setCostoKmBase(tariff.getCostoKmBase());
                    existing.setValorLitroCombustible(tariff.getValorLitroCombustible());
                    existing.setCostoEstadiaDiaria(tariff.getCostoEstadiaDiaria());
                    return tariffRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Tarifa no encontrada"));
    }

    @Override
    public void delete(Long id) {
        tariffRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tariff> findById(Long id) {
        return tariffRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tariff> findAll() {
        return tariffRepository.findAll();
    }
}
