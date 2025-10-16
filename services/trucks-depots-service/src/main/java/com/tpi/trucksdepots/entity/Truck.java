package com.tpi.trucksdepots.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "camiones")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String dominio;

    @NotBlank
    private String transportista;

    private String telefono;

    @NotNull
    private Double capacidadPeso;

    @NotNull
    private Double capacidadVolumen;

    private Double costoKm;

    private Double consumoCombustible;

    private Boolean disponible = Boolean.TRUE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getCapacidadPeso() {
        return capacidadPeso;
    }

    public void setCapacidadPeso(Double capacidadPeso) {
        this.capacidadPeso = capacidadPeso;
    }

    public Double getCapacidadVolumen() {
        return capacidadVolumen;
    }

    public void setCapacidadVolumen(Double capacidadVolumen) {
        this.capacidadVolumen = capacidadVolumen;
    }

    public Double getCostoKm() {
        return costoKm;
    }

    public void setCostoKm(Double costoKm) {
        this.costoKm = costoKm;
    }

    public Double getConsumoCombustible() {
        return consumoCombustible;
    }

    public void setConsumoCombustible(Double consumoCombustible) {
        this.consumoCombustible = consumoCombustible;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}
