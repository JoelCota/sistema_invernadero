package com.itson.invernadero.controller;

import com.itson.invernadero.dto.ActualizarSensorDTO;
import com.itson.invernadero.dto.LecturaSensorDTO;
import com.itson.invernadero.dto.SensorDTO;
import com.itson.invernadero.mappers.LecturaSensorMapper;
import com.itson.invernadero.mappers.SensorMapper;
import com.itson.invernadero.model.LecturaSensor;
import com.itson.invernadero.model.Sensor;
import com.itson.invernadero.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // Obtener todos los sensores
    @GetMapping
    public ResponseEntity<List<SensorDTO>> obtenerTodos() {
        List<Sensor> sensores = sensorService.obtenerTodosLosSensores();
        List<SensorDTO> dtos = sensores.stream()
                .map(SensorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Obtener un sensor por su ID
    @GetMapping("/{id}")
    public Optional<Sensor> obtenerPorId(@PathVariable Long id) {
        return sensorService.obtenerSensorPorId(id);
    }

    // Crear un nuevo sensor
    @PostMapping
    public Sensor registrarSensor(@RequestBody Sensor sensor) {
        return sensorService.registrarSensor(sensor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> actualizarSensor(
            @PathVariable Long id,
            @RequestBody ActualizarSensorDTO actualizarSensorDTO) {
        try {
            Sensor sensorActualizado = sensorService.actualizarSensor(id, actualizarSensorDTO);
            return ResponseEntity.ok(SensorMapper.toDTO(sensorActualizado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // Eliminar un sensor
    @DeleteMapping("/{id}")
    public void eliminarSensor(@PathVariable Long id) {
        sensorService.eliminarSensor(id);
    }

    // Buscar sensores por tipo
    @GetMapping("/tipo/{tipo}")
    public List<Sensor> obtenerPorTipo(@PathVariable String tipo) {
        return sensorService.obtenerSensoresPorTipo(tipo);
    }

    // Buscar sensores por ubicación
    @GetMapping("/ubicacion/{ubicacion}")
    public List<Sensor> obtenerPorUbicacion(@PathVariable String ubicacion) {
        return sensorService.obtenerSensoresPorUbicacion(ubicacion);
    }
}