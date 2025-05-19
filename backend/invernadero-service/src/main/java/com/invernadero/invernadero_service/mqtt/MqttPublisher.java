package com.invernadero.invernadero_service.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invernadero.invernadero_service.dto.AlertaDTO;
import com.invernadero.invernadero_service.model.LecturaSensor;
import com.invernadero.invernadero_service.model.Sensor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttPublisher {

    private final MqttClient mqttClient;
    @Autowired
    private ObjectMapper objectMapper;


    public MqttPublisher() throws Exception {
        this.mqttClient = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
        this.mqttClient.connect();
    }

    public void publicarAlerta(Sensor sensor, LecturaSensor lectura) {
        try {
            AlertaDTO alerta = new AlertaDTO();
            alerta.setSensorId(sensor.getId());
            alerta.setTipo(sensor.getTipo());
            alerta.setUbicacion(sensor.getUbicacion());
            alerta.setValor(lectura.getValor());
            alerta.setValorMinimo(sensor.getValorMinimo());
            alerta.setValorMaximo(sensor.getValorMaximo());
            alerta.setMensaje(String.format(
                    "⚠️Lectura fuera de rango: 12.30 (rango permitido 5.00 - 10.00)",
                    lectura.getValor(), sensor.getValorMinimo(), sensor.getValorMaximo()
            ));

            String json = objectMapper.writeValueAsString(alerta);

            MqttMessage mqttMessage = new MqttMessage(json.getBytes());
            mqttMessage.setQos(1);
            mqttClient.publish("invernadero/alertas", mqttMessage);

            System.out.println("📡 Alerta publicada MQTT: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
