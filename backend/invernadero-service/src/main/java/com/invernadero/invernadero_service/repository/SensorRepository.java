package com.invernadero.invernadero_service.repository;

import com.invernadero.invernadero_service.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByTipo(String tipo);
    Sensor findSensorById(Long id);
    List<Sensor> findByUbicacion(String ubicacion);
}