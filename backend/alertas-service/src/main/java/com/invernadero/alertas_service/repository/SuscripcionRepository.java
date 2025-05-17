package com.invernadero.alertas_service.repository;

import com.invernadero.alertas_service.model.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findBySensorId(Long sensorId);
    List<Suscripcion> findByUserId(Long userId);
}