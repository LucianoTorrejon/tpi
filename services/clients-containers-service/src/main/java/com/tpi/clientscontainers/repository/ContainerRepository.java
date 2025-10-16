package com.tpi.clientscontainers.repository;

import com.tpi.clientscontainers.entity.ContainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContainerRepository extends JpaRepository<ContainerEntity, Long> {
    Optional<ContainerEntity> findByIdentificacion(String identificacion);
    List<ContainerEntity> findByClienteId(Long clienteId);
}
