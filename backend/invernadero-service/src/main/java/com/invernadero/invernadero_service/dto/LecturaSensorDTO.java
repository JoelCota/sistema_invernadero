package com.invernadero.invernadero_service.dto;

import java.time.LocalDateTime;

public class LecturaSensorDTO {
    private Long id;
    private Double valor;
    private LocalDateTime fechaHoraLectura;
    private Long sensorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getFechaHoraLectura() {
        return fechaHoraLectura;
    }

    public void setFechaHoraLectura(LocalDateTime fechaHoraLectura) {
        this.fechaHoraLectura = fechaHoraLectura;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }
}
