// src/main/java/com/example/frontend/model/PlataformaDto.java
package com.example.frontend.model;

public class PlataformaDto {
    private Long idPlataforma;
    private String nombre;
    private String urlOficial;
    private String estado;

    // Constructor vacío
    public PlataformaDto() {}

    // Getters y Setters
    public Long getIdPlataforma() { return idPlataforma; }
    public void setIdPlataforma(Long idPlataforma) { this.idPlataforma = idPlataforma; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUrlOficial() { return urlOficial; }
    public void setUrlOficial(String urlOficial) { this.urlOficial = urlOficial; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}