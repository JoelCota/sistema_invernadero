package com.invernadero.user_service.dtos;

import lombok.Data;

@Data
public class AlarmaDTO {
    private String email;
    private String numeroCelular;

    public AlarmaDTO(String email, String numeroCelular) {
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
