package com.tpi.requestsroutes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "solicitudes", uniqueConstraints = @UniqueConstraint(columnNames = "numero"))
public class TransportRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @NotNull
    private Long contenedorId;

    @NotNull
    private Long clienteId;

    @Positive
    private Double costoEstimado;

    @Positive
    private Double tiempoEstimado;

    private Double costoFinal;

    private Double tiempoReal;

    @Enumerated(EnumType.STRING)
    private RequestStatus estado = RequestStatus.BORRADOR;

    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private RoutePlan ruta;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TransportStatusEvent> eventosEstado = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getContenedorId() {
        return contenedorId;
    }

    public void setContenedorId(Long contenedorId) {
        this.contenedorId = contenedorId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Double getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(Double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public Double getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(Double tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public Double getCostoFinal() {
        return costoFinal;
    }

    public void setCostoFinal(Double costoFinal) {
        this.costoFinal = costoFinal;
    }

    public Double getTiempoReal() {
        return tiempoReal;
    }

    public void setTiempoReal(Double tiempoReal) {
        this.tiempoReal = tiempoReal;
    }

    public RequestStatus getEstado() {
        return estado;
    }

    public void setEstado(RequestStatus estado) {
        this.estado = estado;
    }

    public RoutePlan getRuta() {
        return ruta;
    }

    public void setRuta(RoutePlan ruta) {
        this.ruta = ruta;
    }

    public Set<TransportStatusEvent> getEventosEstado() {
        return eventosEstado;
    }

    public void setEventosEstado(Set<TransportStatusEvent> eventosEstado) {
        this.eventosEstado = eventosEstado;
    }
}
