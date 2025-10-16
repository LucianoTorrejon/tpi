package com.tpi.requestsroutes.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "eventos_estado")
public class TransportStatusEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id")
    private TransportRequest solicitud;

    @Enumerated(EnumType.STRING)
    private RequestStatus estado;

    private OffsetDateTime fechaHora;

    private String descripcion;

    public TransportStatusEvent() {
    }

    public TransportStatusEvent(TransportRequest solicitud, RequestStatus estado, OffsetDateTime fechaHora, String descripcion) {
        this.solicitud = solicitud;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
    }

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

    public RequestStatus getEstado() {
        return estado;
    }

    public void setEstado(RequestStatus estado) {
        this.estado = estado;
    }

    public OffsetDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(OffsetDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
