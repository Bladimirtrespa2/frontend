package com.example.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // ⬅️ Esta línea lo soluciona
public class SuscripcionDto {
    private Long idSuscripcion;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private Long idSuscriptor;
    private Long idPlataforma;
    private Double montoMensual;
    // Añade este campo
    private String timestamp;

    public SuscripcionDto(){}

    // Y su getter/setter
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public Long getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(Long idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdSuscriptor() {
        return idSuscriptor;
    }

    public void setIdSuscriptor(Long idSuscriptor) {
        this.idSuscriptor = idSuscriptor;
    }

    public Long getIdPlataforma() {
        return idPlataforma;
    }

    public void setIdPlataforma(Long idPlataforma) {
        this.idPlataforma = idPlataforma;
    }

    public Double getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(Double montoMensual) {
        this.montoMensual = montoMensual;
    }
}
