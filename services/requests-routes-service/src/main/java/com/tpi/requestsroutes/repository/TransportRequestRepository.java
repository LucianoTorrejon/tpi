package com.tpi.requestsroutes.repository;

import com.tpi.requestsroutes.entity.TransportRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransportRequestRepository extends JpaRepository<TransportRequest, Long> {
    Optional<TransportRequest> findByNumero(String numero);
    Optional<TransportRequest> findFirstByContenedorIdOrderByIdDesc(Long contenedorId);
}
