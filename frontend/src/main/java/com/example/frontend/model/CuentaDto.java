// src/main/java/com/example/frontend/model/CuentaDto.java
package com.example.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaDto {
    private Long idCuenta;
    private String correoCuenta;
    private String contrasenaCuenta;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private Long idPlataforma;
    private String nombrePlataforma;
    private Long idAdministrador;
    private List<PerfilDto> perfiles; // ← Define la lista de perfiles
    private int perfilesOcupados;

    // Constructor vacío (obligatorio para Jackson)
    public CuentaDto() {}

    // Getters y Setters
    public Long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Long idCuenta) { this.idCuenta = idCuenta; }

    public String getCorreoCuenta() { return correoCuenta; }
    public void setCorreoCuenta(String correoCuenta) { this.correoCuenta = correoCuenta; }

    public String getContrasenaCuenta() { return contrasenaCuenta; }
    public void setContrasenaCuenta(String contrasenaCuenta) { this.contrasenaCuenta = contrasenaCuenta; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getIdPlataforma() { return idPlataforma; }
    public void setIdPlataforma(Long idPlataforma) { this.idPlataforma = idPlataforma; }

    public String getNombrePlataforma() { return nombrePlataforma; }
    public void setNombrePlataforma(String nombrePlataforma) { this.nombrePlataforma = nombrePlataforma; }

    public Long getIdAdministrador() { return idAdministrador; }
    public void setIdAdministrador(Long idAdministrador) { this.idAdministrador = idAdministrador; }

    public List<PerfilDto> getPerfiles() { return perfiles; }
    public void setPerfiles(List<PerfilDto> perfiles) { this.perfiles = perfiles; }

    public int getPerfilesOcupados() { return perfilesOcupados; }
    public void setPerfilesOcupados(int perfilesOcupados) { this.perfilesOcupados = perfilesOcupados; }
}