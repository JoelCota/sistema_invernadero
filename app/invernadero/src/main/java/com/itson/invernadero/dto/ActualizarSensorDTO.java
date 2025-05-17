package com.itson.invernadero.dto;

public class ActualizarSensorDTO {
    private String tipo;
    private String ubicacion;
    private Double valorMinimo;
    private Double valorMaximo;
    private Boolean alarmaActiva;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(Double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public Boolean getAlarmaActiva() {
        return alarmaActiva;
    }

    public void setAlarmaActiva(Boolean alarmaActiva) {
        this.alarmaActiva = alarmaActiva;
    }

    public Double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }
}

