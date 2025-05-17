package com.itson.invernadero.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
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
}

