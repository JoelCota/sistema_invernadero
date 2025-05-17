package com.itson.invernadero.dto;

public class SensorDTO {
    private Long id;
    private String tipo;
    private String ubicacion;
    private Double valorMinimo;
    private Double valorMaximo;
    private Boolean alarmaActiva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public Boolean getAlarmaActiva() {
        return alarmaActiva;
    }

    public void setAlarmaActiva(Boolean alarmaActiva) {
        this.alarmaActiva = alarmaActiva;
    }
}
