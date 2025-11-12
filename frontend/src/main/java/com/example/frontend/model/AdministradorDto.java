// src/main/java/com/example/frontend/model/AdministradorDto.java
package com.example.frontend.model;

public class AdministradorDto {
    private Long idAdministrador;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;

    // Getters y Setters
    public Long getIdAdministrador() { return idAdministrador; }
    public void setIdAdministrador(Long idAdministrador) { this.idAdministrador = idAdministrador; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}