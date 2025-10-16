package com.tpi.ratescosts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "tarifas")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Double costoKmBase;

    @NotNull
    @Positive
    private Double valorLitroCombustible;

    @NotNull
    @Positive
    private Double costoEstadiaDiaria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCostoKmBase() {
        return costoKmBase;
    }

    public void setCostoKmBase(Double costoKmBase) {
        this.costoKmBase = costoKmBase;
    }

    public Double getValorLitroCombustible() {
        return valorLitroCombustible;
    }

    public void setValorLitroCombustible(Double valorLitroCombustible) {
        this.valorLitroCombustible = valorLitroCombustible;
    }

    public Double getCostoEstadiaDiaria() {
        return costoEstadiaDiaria;
    }

    public void setCostoEstadiaDiaria(Double costoEstadiaDiaria) {
        this.costoEstadiaDiaria = costoEstadiaDiaria;
    }
}
