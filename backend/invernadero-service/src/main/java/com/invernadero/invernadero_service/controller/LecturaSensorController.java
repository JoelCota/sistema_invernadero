package com.invernadero.invernadero_service.controller;

import com.invernadero.invernadero_service.dto.LecturaSensorDTO;
import com.invernadero.invernadero_service.dto.RegLecturaSensorDTO;
import com.invernadero.invernadero_service.mapper.LecturaSensorMapper;
import com.invernadero.invernadero_service.model.LecturaSensor;
import com.invernadero.invernadero_service.service.LecturaSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sensores/{sensorId}/lecturas")
public class LecturaSensorController {

    private final LecturaSensorService lecturaSensorService;

    @Autowired
    public LecturaSensorController(LecturaSensorService lecturaSensorService) {
        this.lecturaSensorService = lecturaSensorService;
    }


    @PostMapping
    public ResponseEntity<LecturaSensorDTO> registrarLectura(
            @PathVariable Long sensorId,
            @RequestBody RegLecturaSensorDTO dto) {
    LecturaSensor lectura = lecturaSensorService.registrarLectura(sensorId,dto);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(LecturaSensorMapper.toDTO(lectura));
}

    @GetMapping
    public ResponseEntity<List<LecturaSensorDTO>>  obtenerLecturas(@PathVariable Long sensorId) {
        List<LecturaSensor> lecturas = lecturaSensorService.obtenerLecturas(sensorId);
        List<LecturaSensorDTO> dtos = lecturas.stream()
                .map(LecturaSensorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
