package com.tpi.trucksdepots.service.impl;

import com.tpi.trucksdepots.entity.Depot;
import com.tpi.trucksdepots.repository.DepotRepository;
import com.tpi.trucksdepots.service.DepotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepotServiceImpl implements DepotService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepotServiceImpl.class);
    private final DepotRepository depotRepository;

    public DepotServiceImpl(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    @Override
    public Depot create(Depot depot) {
        LOGGER.info("Creating depot {}", depot.getNombre());
        return depotRepository.save(depot);
    }

    @Override
    public Depot update(Long id, Depot depot) {
        return depotRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(depot.getNombre());
                    existing.setDireccion(depot.getDireccion());
                    existing.setLatitud(depot.getLatitud());
                    existing.setLongitud(depot.getLongitud());
                    return depotRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Deposito no encontrado"));
    }

    @Override
    public void delete(Long id) {
        depotRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Depot> findAll() {
        return depotRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Depot> findById(Long id) {
        return depotRepository.findById(id);
    }
}
