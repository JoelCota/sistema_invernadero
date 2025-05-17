package com.invernadero.invernadero_service.mapper;


import com.invernadero.invernadero_service.dto.ActualizarSensorDTO;
import com.invernadero.invernadero_service.dto.SensorDTO;
import com.invernadero.invernadero_service.model.Sensor;

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

    public static void actualizarEntidadDesdeDTO(Sensor sensor, ActualizarSensorDTO dto) {
        sensor.setTipo(dto.getTipo());
        sensor.setUbicacion(dto.getUbicacion());
        sensor.setValorMinimo(dto.getValorMinimo());
        sensor.setValorMaximo(dto.getValorMaximo());
        sensor.setAlarmaActiva(dto.getAlarmaActiva());
    }
}
