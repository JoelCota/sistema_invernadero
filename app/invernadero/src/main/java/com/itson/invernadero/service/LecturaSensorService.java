package com.itson.invernadero.service;


import com.itson.invernadero.dto.LecturaSensorDTO;
import com.itson.invernadero.dto.RegLecturaSensorDTO;
import com.itson.invernadero.model.LecturaSensor;
import com.itson.invernadero.model.Sensor;
import com.itson.invernadero.repository.LecturaSensorRepository;
import com.itson.invernadero.repository.SensorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LecturaSensorService {

    private final SensorRepository sensorRepository;
    private final LecturaSensorRepository lecturaSensorRepository;

    public LecturaSensorService(SensorRepository sensorRepository,
                                LecturaSensorRepository lecturaSensorRepository) {
        this.sensorRepository = sensorRepository;
        this.lecturaSensorRepository = lecturaSensorRepository;
    }
    public LecturaSensor registrarLectura(Long sensorId, RegLecturaSensorDTO dto) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new RuntimeException("Sensor no encontrado con ID: " + sensorId));

        LecturaSensor lectura = new LecturaSensor();
        lectura.setSensor(sensor);
        lectura.setValor(dto.getValor());
        lectura.setFechaHoraLectura(
                dto.getFechaHoraLectura() != null ? dto.getFechaHoraLectura() : LocalDateTime.now()
        );

        // Establecer la fecha si no viene en el JSON
        if (lectura.getFechaHoraLectura() == null) {
            lectura.setFechaHoraLectura(LocalDateTime.now());
        }

        // Verificación de alarma
        if (sensor.getAlarmaActiva()) {
            Double valor = lectura.getValor();
            if (valor < sensor.getValorMinimo() || valor > sensor.getValorMaximo()) {
                // Aquí puedes disparar una acción: log, guardar alerta, enviar correo, etc.
                System.out.println("⚠️  ¡Alarma! Lectura fuera del rango permitido: " + valor);
            }
        }

        return lecturaSensorRepository.save(lectura);
    }

    public List<LecturaSensor> obtenerLecturas(Long sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new RuntimeException("Sensor no encontrado con ID: " + sensorId));

        return lecturaSensorRepository.findBySensor(sensor);
    }
}
