package com.invernadero.alertas_service.service;

import com.invernadero.alertas_service.model.Suscripcion;
import com.invernadero.alertas_service.repository.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;

    public SuscripcionService(SuscripcionRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
    }

    public Suscripcion suscribirUsuario(Long userId, Long sensorId) {
        // Para evitar duplicados, opcional: validar que no exista ya esa suscripción
        List<Suscripcion> existentes = suscripcionRepository.findByUserId(userId);
        boolean yaSuscripto = existentes.stream()
                .anyMatch(s -> s.getSensorId().equals(sensorId));
        if (yaSuscripto) {
            return null; // o lanzar excepción o devolver la suscripción existente
        }
        Suscripcion suscripcion = new Suscripcion(userId, sensorId);
        return suscripcionRepository.save(suscripcion);
    }

    public List<Suscripcion> obtenerTodasLasSuscripciones() {
        return suscripcionRepository.findAll(); // Asegúrate de tener el repository inyectado
    }

    public List<Suscripcion> obtenerSuscripcionesPorSensor(Long sensorId) {
        return suscripcionRepository.findBySensorId(sensorId);
    }
}
