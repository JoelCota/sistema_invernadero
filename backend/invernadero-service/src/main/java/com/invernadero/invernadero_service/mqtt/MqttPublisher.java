package com.invernadero.invernadero_service.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invernadero.invernadero_service.dto.AlertaDTO;
import com.invernadero.invernadero_service.model.LecturaSensor;
import com.invernadero.invernadero_service.model.Sensor;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqttPublisher {

    private final MqttClient mqttClient;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public MqttPublisher(@Value("${mqtt.broker.url}") String brokerUrl) throws Exception {
        this.mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId());
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
                    "⚠️Lectura fuera de rango: %.2f (rango permitido %.2f - %.2f)",
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

    @PostConstruct
    public void init() {
        try {
            this.mqttClient.connect();
        } catch (Exception e) {
            e.printStackTrace(); // puedes agregar logs
        }
    }
}
