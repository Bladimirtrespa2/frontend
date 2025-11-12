// src/main/java/com/example/frontend/model/SuscriptorDto.java
package com.example.frontend.model;

public class SuscriptorDto {
    private Long idSuscriptor;
    private String nombre;
    private String correo;

    // Constructor vacío (obligatorio para Jackson)
    public SuscriptorDto() {}

    // Getters y Setters
    public Long getIdSuscriptor() { return idSuscriptor; }
    public void setIdSuscriptor(Long idSuscriptor) { this.idSuscriptor = idSuscriptor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}
