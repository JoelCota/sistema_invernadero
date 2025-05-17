package com.itson.invernadero.controller;

import com.itson.invernadero.dto.LecturaSensorDTO;
import com.itson.invernadero.dto.RegLecturaSensorDTO;
import com.itson.invernadero.mappers.LecturaSensorMapper;
import com.itson.invernadero.model.LecturaSensor;
import com.itson.invernadero.service.LecturaSensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sensores/{sensorId}/lecturas")
public class LecturaSensorController {

    private final LecturaSensorService lecturaSensorService;

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
