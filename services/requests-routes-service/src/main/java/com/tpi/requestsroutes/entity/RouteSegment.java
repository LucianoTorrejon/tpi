package com.tpi.requestsroutes.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "tramos")
public class RouteSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_id")
    private RoutePlan ruta;

    private String origen;

    private String destino;

    private String tipo;

    @Enumerated(EnumType.STRING)
    private SegmentStatus estado = SegmentStatus.PENDIENTE;

    private Double costoAprox;

    private Double costoReal;

    private OffsetDateTime fechaHoraInicio;

    private OffsetDateTime fechaHoraFin;

    private Long camionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoutePlan getRuta() {
        return ruta;
    }

    public void setRuta(RoutePlan ruta) {
        this.ruta = ruta;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public SegmentStatus getEstado() {
        return estado;
    }

    public void setEstado(SegmentStatus estado) {
        this.estado = estado;
    }

    public Double getCostoAprox() {
        return costoAprox;
    }

    public void setCostoAprox(Double costoAprox) {
        this.costoAprox = costoAprox;
    }

    public Double getCostoReal() {
        return costoReal;
    }

    public void setCostoReal(Double costoReal) {
        this.costoReal = costoReal;
    }

    public OffsetDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(OffsetDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public OffsetDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(OffsetDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public Long getCamionId() {
        return camionId;
    }

    public void setCamionId(Long camionId) {
        this.camionId = camionId;
    }
}
