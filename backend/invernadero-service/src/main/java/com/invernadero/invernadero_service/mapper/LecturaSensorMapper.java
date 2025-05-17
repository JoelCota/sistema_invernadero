package com.invernadero.invernadero_service.mapper;


import com.invernadero.invernadero_service.dto.ActualizarSensorDTO;
import com.invernadero.invernadero_service.dto.LecturaSensorDTO;
import com.invernadero.invernadero_service.model.LecturaSensor;
import com.invernadero.invernadero_service.model.Sensor;

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
