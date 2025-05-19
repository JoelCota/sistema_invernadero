package com.invernadero.alertas_service.dto;


import lombok.Data;

@Data
public class UsuarioDTO {
    private String email;
    private String numeroCelular;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String email, String numeroCelular) {
        this.email = email;
        this.numeroCelular = numeroCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }
}

