package com.tpi.requestsroutes.repository;

import com.tpi.requestsroutes.entity.TransportStatusEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportStatusEventRepository extends JpaRepository<TransportStatusEvent, Long> {
    List<TransportStatusEvent> findBySolicitudIdOrderByFechaHoraAsc(Long solicitudId);
}
