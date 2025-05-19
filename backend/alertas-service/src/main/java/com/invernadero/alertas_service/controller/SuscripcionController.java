package com.invernadero.alertas_service.controller;

import com.invernadero.alertas_service.model.Suscripcion;
import com.invernadero.alertas_service.service.SuscripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/alertas")
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    public SuscripcionController(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }
    @GetMapping
    public ResponseEntity<?> obtenerTodasLasAlertas() {
        return ResponseEntity.ok(suscripcionService.obtenerTodasLasSuscripciones());
    }

    @PostMapping("/suscribir")
    public ResponseEntity<?> suscribirUsuario(@RequestBody Map<String, Long> body) {
        Long userId = body.get("idUsuario");
        Long sensorId = body.get("idSensor");
        Suscripcion suscripcion = suscripcionService.suscribirUsuario(userId, sensorId);
        if (suscripcion == null) {
            return ResponseEntity.badRequest().body("El usuario ya está suscrito a este sensor.");
        }
        return ResponseEntity.ok(suscripcion);
    }

}
