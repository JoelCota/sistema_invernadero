package com.invernadero.invernadero_service.controller;

import com.invernadero.invernadero_service.dto.ActualizarSensorDTO;
import com.invernadero.invernadero_service.dto.SensorDTO;
import com.invernadero.invernadero_service.mapper.SensorMapper;
import com.invernadero.invernadero_service.model.Sensor;
import com.invernadero.invernadero_service.security.JwtUtil;
import com.invernadero.invernadero_service.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @Autowired
    private SensorService sensorService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RestTemplate restTemplate;
    @Value("${alertas.service.url}")
    private String alertasServiceUrl;
    // Obtener todos los sensores con DTOs
    @GetMapping
    public ResponseEntity<List<SensorDTO>> obtenerTodos() {
        List<Sensor> sensores = sensorService.obtenerTodosLosSensores();
        List<SensorDTO> dtos = sensores.stream()
                .map(SensorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> obtenerPorId(@PathVariable Long id) {
        Optional<Sensor> sensorOpt = Optional.ofNullable(sensorService.obtenerSensorPorId(id));
        if (sensorOpt.isPresent()) {
            SensorDTO dto = SensorMapper.toDTO(sensorOpt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<SensorDTO> registrarSensor(
            @RequestBody Sensor sensor,
            @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtUtil.getUserIdFromToken(authorizationHeader);
        System.out.println(userId);
        Sensor creado = sensorService.registrarSensor(sensor);
        suscribirUsuarioASensor(userId, creado.getId());

        return ResponseEntity.status(201).body(SensorMapper.toDTO(creado));
    }

    private void suscribirUsuarioASensor(Long idUsuario, Long idSensor) {
        System.out.println(idUsuario);
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("idUsuario", idUsuario);
            payload.put("idSensor", idSensor);

            restTemplate.postForEntity(alertasServiceUrl + "/suscribir", payload, Void.class);
        } catch (Exception e) {
            System.err.println("Error al suscribir usuario al servicio de alertas: " + e.getMessage());
        }
    }


    // Actualizar sensor
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

    // Eliminar sensor (puedes devolver 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSensor(@PathVariable Long id) {
        sensorService.eliminarSensor(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar sensores por tipo (con DTOs)
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<SensorDTO>> obtenerPorTipo(@PathVariable String tipo) {
        List<Sensor> sensores = sensorService.obtenerSensoresPorTipo(tipo);
        List<SensorDTO> dtos = sensores.stream()
                .map(SensorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Buscar sensores por ubicación (con DTOs)
    @GetMapping("/ubicacion/{ubicacion}")
    public ResponseEntity<List<SensorDTO>> obtenerPorUbicacion(@PathVariable String ubicacion) {
        List<Sensor> sensores = sensorService.obtenerSensoresPorUbicacion(ubicacion);
        List<SensorDTO> dtos = sensores.stream()
                .map(SensorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
