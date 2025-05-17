package com.itson.invernadero.repository;

import com.itson.invernadero.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByTipo(String tipo);
    List<Sensor> findByUbicacion(String ubicacion);
}