package com.itson.invernadero.service;

import com.itson.invernadero.dto.ActualizarSensorDTO;
import com.itson.invernadero.model.Sensor;
import com.itson.invernadero.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    // Obtener todos los sensores
    public List<Sensor> obtenerTodosLosSensores() {
        return sensorRepository.findAll();
    }

    // Obtener un sensor por su ID
    public Optional<Sensor> obtenerSensorPorId(Long id) {
        return sensorRepository.findById(id);
    }

    // Crear o actualizar un sensor
    public Sensor registrarSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    // Eliminar un sensor
    public void eliminarSensor(Long id) {
        sensorRepository.deleteById(id);
    }

    // Buscar por tipo
    public List<Sensor> obtenerSensoresPorTipo(String tipo) {
        return sensorRepository.findByTipo(tipo);
    }

    // Buscar por ubicación
    public List<Sensor> obtenerSensoresPorUbicacion(String ubicacion) {
        return sensorRepository.findByUbicacion(ubicacion);
    }

    public Sensor actualizarSensor(Long id, ActualizarSensorDTO dto) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sensor no encontrado"));
//        if (dto.getTipo() != null) sensor.setTipo(dto.getTipo());
//        if (dto.getUbicacion() != null) sensor.setUbicacion(dto.getUbicacion());
//        if (dto.getValorMinimo() != null) sensor.setValorMinimo(dto.getValorMinimo());
//        if (dto.getValorMaximo() != null) sensor.setValorMaximo(dto.getValorMaximo());
//        if (dto.getAlarmaActiva() != null) sensor.setAlarmaActiva(dto.getAlarmaActiva());

        return sensorRepository.save(sensor);
    }

}
