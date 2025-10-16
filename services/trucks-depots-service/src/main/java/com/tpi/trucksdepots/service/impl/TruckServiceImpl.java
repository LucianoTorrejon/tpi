package com.tpi.trucksdepots.service.impl;

import com.tpi.trucksdepots.entity.Truck;
import com.tpi.trucksdepots.repository.TruckRepository;
import com.tpi.trucksdepots.service.TruckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TruckServiceImpl implements TruckService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TruckServiceImpl.class);
    private final TruckRepository truckRepository;

    public TruckServiceImpl(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @Override
    public Truck create(Truck truck) {
        LOGGER.info("Registering truck {}", truck.getDominio());
        return truckRepository.save(truck);
    }

    @Override
    public Truck update(Long id, Truck truck) {
        return truckRepository.findById(id)
                .map(existing -> {
                    existing.setDominio(truck.getDominio());
                    existing.setTransportista(truck.getTransportista());
                    existing.setTelefono(truck.getTelefono());
                    existing.setCapacidadPeso(truck.getCapacidadPeso());
                    existing.setCapacidadVolumen(truck.getCapacidadVolumen());
                    existing.setCostoKm(truck.getCostoKm());
                    existing.setConsumoCombustible(truck.getConsumoCombustible());
                    existing.setDisponible(truck.getDisponible());
                    return truckRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Camion no encontrado"));
    }

    @Override
    public void delete(Long id) {
        truckRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Truck> findAll() {
        return truckRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Truck> findAvailable() {
        return truckRepository.findByDisponibleTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Truck> findById(Long id) {
        return truckRepository.findById(id);
    }

    @Override
    public boolean validateCapacity(Long truckId, double peso, double volumen) {
        Truck truck = truckRepository.findById(truckId)
                .orElseThrow(() -> new IllegalArgumentException("Camion no encontrado"));
        boolean valid = peso <= truck.getCapacidadPeso() && volumen <= truck.getCapacidadVolumen();
        LOGGER.info("Validating capacity for truck {} - peso {}, volumen {}, resultado {}",
                truck.getDominio(), peso, volumen, valid);
        return valid;
    }
}
