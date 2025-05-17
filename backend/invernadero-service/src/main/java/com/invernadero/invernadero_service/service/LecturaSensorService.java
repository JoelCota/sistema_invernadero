package com.invernadero.invernadero_service.service;

import com.invernadero.invernadero_service.dto.RegLecturaSensorDTO;
import com.invernadero.invernadero_service.model.LecturaSensor;

import java.util.List;

public interface LecturaSensorService {
    LecturaSensor registrarLectura(Long sensorId, RegLecturaSensorDTO dto);
    List<LecturaSensor> obtenerLecturas(Long sensorId);
}
