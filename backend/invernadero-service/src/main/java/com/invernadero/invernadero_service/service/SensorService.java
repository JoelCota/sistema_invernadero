package com.invernadero.invernadero_service.service;

import com.invernadero.invernadero_service.dto.ActualizarSensorDTO;
import com.invernadero.invernadero_service.model.Sensor;

import java.util.List;
import java.util.Optional;

public interface SensorService {
    List<Sensor> obtenerTodosLosSensores();
    Sensor obtenerSensorPorId(Long id);
    Sensor registrarSensor(Sensor sensor);
    void eliminarSensor(Long id);
    List<Sensor> obtenerSensoresPorTipo(String tipo);
    List<Sensor> obtenerSensoresPorUbicacion(String ubicacion);
    Sensor actualizarSensor(Long id, ActualizarSensorDTO actualizarSensorDTO);
}
