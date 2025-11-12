// src/main/java/com/example/frontend/model/PerfilDto.java
package com.example.frontend.model;

public class PerfilDto {
    private Long idPerfil;
    private String nombrePerfil;
    private String pin;
    private String estado;
    private Long idCuenta;

    // Getters y Setters
    public Long getIdPerfil() { return idPerfil; }
    public void setIdPerfil(Long idPerfil) { this.idPerfil = idPerfil; }

    public String getNombrePerfil() { return nombrePerfil; }
    public void setNombrePerfil(String nombrePerfil) { this.nombrePerfil = nombrePerfil; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Long idCuenta) { this.idCuenta = idCuenta; }
}