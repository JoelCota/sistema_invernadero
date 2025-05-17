package com.itson.invernadero.dto;

import java.time.LocalDateTime;

public class RegLecturaSensorDTO {
    private Double valor;
    private LocalDateTime fechaHoraLectura; // opcional, si no se envía, usar now()

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
