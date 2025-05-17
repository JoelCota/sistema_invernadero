package com.invernadero.invernadero_service.service.impl;

import com.invernadero.invernadero_service.dto.ActualizarSensorDTO;
import com.invernadero.invernadero_service.model.Sensor;
import com.invernadero.invernadero_service.repository.SensorRepository;
import com.invernadero.invernadero_service.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SensorServiceImpl implements SensorService {


    private final SensorRepository sensorRepository;


    public SensorServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public List<Sensor> obtenerTodosLosSensores() {
        return sensorRepository.findAll();
    }

    @Override
    public Sensor obtenerSensorPorId(Long id) {
        return sensorRepository.findSensorById(id);
    }

    @Override
    public Sensor registrarSensor(Sensor sensor) {
        return   sensorRepository.save(sensor);
    }

    @Override
    public void eliminarSensor(Long id) {
        sensorRepository.deleteById(id);
    }

    @Override
    public List<Sensor> obtenerSensoresPorTipo(String tipo) {
        return sensorRepository.findByTipo(tipo);
    }

    @Override
    public List<Sensor> obtenerSensoresPorUbicacion(String ubicacion) {
        return sensorRepository.findByUbicacion(ubicacion);
    }

    @Override
    public Sensor actualizarSensor(Long id, ActualizarSensorDTO dto) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sensor no encontrado con id: " + id));

        sensor.setValorMinimo(dto.getValorMinimo());
        sensor.setValorMaximo(dto.getValorMaximo());
        sensor.setAlarmaActiva(dto.getAlarmaActiva());

        return sensorRepository.save(sensor);
    }
}
