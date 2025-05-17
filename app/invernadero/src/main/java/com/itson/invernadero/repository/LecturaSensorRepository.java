package com.itson.invernadero.repository;

import com.itson.invernadero.model.LecturaSensor;
import com.itson.invernadero.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LecturaSensorRepository extends JpaRepository<LecturaSensor, Long> {
    List<LecturaSensor> findBySensor(Sensor sensor);
}
