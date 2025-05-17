package com.invernadero.invernadero_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class RegLecturaSensorDTO {
    private Double valor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraLectura;

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
}
