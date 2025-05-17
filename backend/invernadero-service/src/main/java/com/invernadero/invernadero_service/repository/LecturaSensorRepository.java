package com.invernadero.invernadero_service.repository;

import com.invernadero.invernadero_service.model.LecturaSensor;
import com.invernadero.invernadero_service.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LecturaSensorRepository extends JpaRepository<LecturaSensor, Long> {
    List<LecturaSensor> findBySensor(Sensor sensor);
    List<LecturaSensor> findBySensorId(Long sensorId);

}
