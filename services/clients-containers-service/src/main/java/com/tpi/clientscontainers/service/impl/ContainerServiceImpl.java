package com.tpi.clientscontainers.service.impl;

import com.tpi.clientscontainers.entity.ContainerEntity;
import com.tpi.clientscontainers.repository.ClientRepository;
import com.tpi.clientscontainers.repository.ContainerRepository;
import com.tpi.clientscontainers.service.ContainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContainerServiceImpl implements ContainerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerServiceImpl.class);
    private final ContainerRepository containerRepository;
    private final ClientRepository clientRepository;

    public ContainerServiceImpl(ContainerRepository containerRepository, ClientRepository clientRepository) {
        this.containerRepository = containerRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public ContainerEntity create(ContainerEntity containerEntity) {
        LOGGER.info("Creating container {}", containerEntity.getIdentificacion());
        return containerRepository.save(containerEntity);
    }

    @Override
    public ContainerEntity update(Long id, ContainerEntity containerEntity) {
        return containerRepository.findById(id)
                .map(existing -> {
                    existing.setIdentificacion(containerEntity.getIdentificacion());
                    existing.setEstado(containerEntity.getEstado());
                    existing.setPeso(containerEntity.getPeso());
                    existing.setVolumen(containerEntity.getVolumen());
                    existing.setClienteId(containerEntity.getClienteId());
                    return containerRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Contenedor no encontrado"));
    }

    @Override
    public void delete(Long id) {
        containerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContainerEntity> findAll() {
        return containerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContainerEntity> findById(Long id) {
        return containerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContainerEntity> findByCliente(Long clienteId) {
        return containerRepository.findByClienteId(clienteId);
    }

    @Override
    public ContainerEntity assignToClient(Long containerId, Long clientId) {
        ContainerEntity container = containerRepository.findById(containerId)
                .orElseThrow(() -> new IllegalArgumentException("Contenedor no encontrado"));
        clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        container.setClienteId(clientId);
        return containerRepository.save(container);
    }
}
