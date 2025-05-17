package com.itson.invernadero.mappers;

import com.itson.invernadero.dto.SensorDTO;
import com.itson.invernadero.model.Sensor;

public class SensorMapper {
    public static SensorDTO toDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setTipo(sensor.getTipo());
        dto.setUbicacion(sensor.getUbicacion());
        dto.setValorMinimo(sensor.getValorMinimo());
        dto.setValorMaximo(sensor.getValorMaximo());
        dto.setAlarmaActiva(sensor.getAlarmaActiva());
        return dto;
    }
}
