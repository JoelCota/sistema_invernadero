package com.invernadero.invernadero_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "sensores")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String ubicacion;

    // Configuración de alarma
    private Double valorMinimo;
    private Double valorMaximo;
    private Boolean alarmaActiva;

    // Relación con lecturas
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<LecturaSensor> lecturas;

    public boolean getAlarmaActiva() {
        return alarmaActiva;
    }

    public Double getValorMinimo() {
        return valorMinimo;
    }

    public Double getValorMaximo() {
        return valorMaximo;
    }

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

    public void setValorMinimo(Double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public void setValorMaximo(Double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public void setAlarmaActiva(Boolean alarmaActiva) {
        this.alarmaActiva = alarmaActiva;
    }

    public List<LecturaSensor> getLecturas() {
        return lecturas;
    }

    public void setLecturas(List<LecturaSensor> lecturas) {
        this.lecturas = lecturas;
    }
}

