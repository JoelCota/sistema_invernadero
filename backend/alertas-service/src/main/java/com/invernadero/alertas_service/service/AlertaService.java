package com.invernadero.alertas_service.service;

import com.invernadero.alertas_service.dto.AlertaDTO;
import com.invernadero.alertas_service.dto.UsuarioDTO;
import com.invernadero.alertas_service.model.Suscripcion;
import com.invernadero.alertas_service.model.UsuarioClient;
import com.invernadero.alertas_service.security.JwtUtil;
import com.invernadero.alertas_service.service.SuscripcionService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaService {

    private final SuscripcionService suscripcionService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JwtUtil jwtUtil;
    private final UsuarioClient usuarioClient;

    public AlertaService(SuscripcionService suscripcionService, JavaMailSender mailSender, UsuarioClient usuarioClient) {
        this.suscripcionService = suscripcionService;
        this.mailSender = mailSender;
        this.usuarioClient = usuarioClient;
    }

    public void procesarAlerta(AlertaDTO alerta) {
        Long sensorId=alerta.getSensorId();
        String sensor = alerta.getUbicacion();
        String detalleAlerta = alerta.getMensaje();
        List<Suscripcion> suscripciones = suscripcionService.obtenerSuscripcionesPorSensor(sensorId);
        for (Suscripcion sus : suscripciones) {
            UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorId(sus.getUserId());
            enviarSmsAlerta(usuario.getNumeroCelular(), "Alerta en " + sensor + ": " + detalleAlerta);
            enviarEmailAlerta(usuario.getEmail(), sensor, detalleAlerta);
        }
    }



    private void enviarEmailAlerta(String email, String sensor, String detalle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Alarma: Lectura fuera de rango en " + sensor);
        message.setText(detalle);
        mailSender.send(message);
    }


    private void enviarSmsAlerta(String numeroDestino, String mensaje) {
        Message.creator(
                new PhoneNumber("+" + numeroDestino),
                new PhoneNumber("+13412127027"), // Número de Twilio
                mensaje
        ).create();
    }


}
