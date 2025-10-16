package com.tpi.clientscontainers.service;

import com.tpi.clientscontainers.entity.ContainerEntity;

import java.util.List;
import java.util.Optional;

public interface ContainerService {
    ContainerEntity create(ContainerEntity containerEntity);
    ContainerEntity update(Long id, ContainerEntity containerEntity);
    void delete(Long id);
    List<ContainerEntity> findAll();
    Optional<ContainerEntity> findById(Long id);
    List<ContainerEntity> findByCliente(Long clienteId);
    ContainerEntity assignToClient(Long containerId, Long clientId);
}
