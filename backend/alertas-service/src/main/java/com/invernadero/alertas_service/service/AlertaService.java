package com.invernadero.alertas_service.service;

import com.invernadero.alertas_service.dto.AlertaDTO;
import com.invernadero.alertas_service.model.Suscripcion;
import com.invernadero.alertas_service.service.SuscripcionService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaService {

    private final SuscripcionService suscripcionService;
    private final JavaMailSender mailSender;

    public AlertaService(SuscripcionService suscripcionService, JavaMailSender mailSender) {
        this.suscripcionService = suscripcionService;
        this.mailSender = mailSender;
    }

    public void procesarAlerta(AlertaDTO alerta) {
        // Parsear JSON para extraer sensorId y detalles (usa librería Jackson o similar)
        // Ejemplo rápido con pseudo-código:
        // Long sensorId = ... extraer del mensajeJson
        // String detalleAlerta = ... extraer mensaje

        // Aquí ejemplo estático para ilustrar:
        Long sensorId=alerta.getSensorId();
        String sensor = alerta.getUbicacion();
        System.out.println(sensor);
        String detalleAlerta = alerta.getMensaje();
        List<Suscripcion> suscripciones = suscripcionService.obtenerSuscripcionesPorSensor(sensorId);
        enviarEmailAlerta("suscripciones.get(0).",sensor,detalleAlerta);
        enviarSmsAlerta("telefono", "Alerta en " + sensor + ": " + detalleAlerta);
//        for (Suscripcion sus : suscripciones) {
//            // Aquí idealmente consultamos email por userId en user-service (REST client o bus)
//            // Por ahora supongamos email fijo:
//            String email = obtenerEmailPorUserId(sus.getUserId());
//
//            enviarEmailAlerta(email, sensorId, detalleAlerta);
//        }
    }



    private void enviarEmailAlerta(String email, String sensor, String detalle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("bearboyc@gmail.com"); // Podría ser configurable o venir del sensor/usuario
        message.setSubject("Alarma: Lectura fuera de rango en " + sensor);
        message.setText(detalle);

        mailSender.send(message);
    }

    private String obtenerEmailPorUserId(Long userId) {
        // Aquí deberías hacer una llamada REST al user-service para obtener email por userId.
        // Por ahora retornamos un email dummy para pruebas:
        return "usuario"+userId+"@ejemplo.com";
    }

    private void enviarSmsAlerta(String numeroDestino, String mensaje) {
        Message.creator(
                new PhoneNumber("+526871399822"),
                new PhoneNumber("+13412127027"), // Número de Twilio
                mensaje
        ).create();
    }


}
