package com.itson.invernadero.mappers;

import com.itson.invernadero.dto.LecturaSensorDTO;
import com.itson.invernadero.dto.SensorDTO;
import com.itson.invernadero.model.LecturaSensor;
import com.itson.invernadero.model.Sensor;

public class LecturaSensorMapper {
    public static LecturaSensorDTO toDTO(LecturaSensor lecturaSensor) {
        LecturaSensorDTO dto = new LecturaSensorDTO();
        dto.setId(lecturaSensor.getId());
        dto.setSensorId(lecturaSensor.getSensor().getId());
        dto.setValor(lecturaSensor.getValor());
        dto.setFechaHoraLectura(lecturaSensor.getFechaHoraLectura());
        return dto;
    }

}
