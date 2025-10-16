package com.tpi.requestsroutes.repository;

import com.tpi.requestsroutes.entity.RouteSegment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteSegmentRepository extends JpaRepository<RouteSegment, Long> {
}
