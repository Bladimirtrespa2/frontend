// src/main/java/com/example/frontend/model/PagoDto.java
package com.example.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // ⬅️ Esta línea lo soluciona
public class PagoDto {
    private Long idPago;
    private String fechaPago;
    private Double montoPagado;
    private String metodoPago;
    private Long idSuscripcion;

    // Constructor vacío
    public PagoDto() {}

    // Getters y Setters
    public Long getIdPago() { return idPago; }
    public void setIdPago(Long idPago) { this.idPago = idPago; }

    public String getFechaPago() { return fechaPago; }
    public void setFechaPago(String fechaPago) { this.fechaPago = fechaPago; }

    public Double getMontoPagado() { return montoPagado; }
    public void setMontoPagado(Double montoPagado) { this.montoPagado = montoPagado; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public Long getIdSuscripcion() { return idSuscripcion; }
    public void setIdSuscripcion(Long idSuscripcion) { this.idSuscripcion = idSuscripcion; }
}