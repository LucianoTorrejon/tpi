package com.tpi.requestsroutes.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rutas")
public class RoutePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private TransportRequest solicitud;

    private Integer cantidadTramos;

    private Integer cantidadDepositos;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RouteSegment> tramos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransportRequest getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(TransportRequest solicitud) {
        this.solicitud = solicitud;
    }

    public Integer getCantidadTramos() {
        return cantidadTramos;
    }

    public void setCantidadTramos(Integer cantidadTramos) {
        this.cantidadTramos = cantidadTramos;
    }

    public Integer getCantidadDepositos() {
        return cantidadDepositos;
    }

    public void setCantidadDepositos(Integer cantidadDepositos) {
        this.cantidadDepositos = cantidadDepositos;
    }

    public List<RouteSegment> getTramos() {
        return tramos;
    }

    public void setTramos(List<RouteSegment> tramos) {
        this.tramos = tramos;
    }
}
