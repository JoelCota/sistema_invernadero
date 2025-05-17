package com.invernadero.invernadero_service.service.impl;

import com.invernadero.invernadero_service.dto.RegLecturaSensorDTO;
import com.invernadero.invernadero_service.model.LecturaSensor;
import com.invernadero.invernadero_service.model.Sensor;
import com.invernadero.invernadero_service.mqtt.MqttPublisher;
import com.invernadero.invernadero_service.repository.LecturaSensorRepository;
import com.invernadero.invernadero_service.repository.SensorRepository;
import com.invernadero.invernadero_service.service.LecturaSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LecturaSensorServiceImpl implements LecturaSensorService {

    private final SensorRepository sensorRepository;
    private final LecturaSensorRepository lecturaSensorRepository;
    private final MqttPublisher mqttPublisher;

    @Autowired
    public LecturaSensorServiceImpl(SensorRepository sensorRepository,
                                    LecturaSensorRepository lecturaSensorRepository,
                                    MqttPublisher mqttPublisher) {
        this.sensorRepository = sensorRepository;
        this.lecturaSensorRepository = lecturaSensorRepository;
        this.mqttPublisher = mqttPublisher;
    }


    public LecturaSensorServiceImpl(LecturaSensorRepository lecturaSensorRepository,
                                    SensorRepository sensorRepository, MqttPublisher mqttPublisher) {
        this.lecturaSensorRepository = lecturaSensorRepository;
        this.sensorRepository = sensorRepository;
        this.mqttPublisher = mqttPublisher;
    }

    @Override
    public LecturaSensor registrarLectura(Long sensorId, RegLecturaSensorDTO dto) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new NoSuchElementException("Sensor no encontrado"));

        LecturaSensor lectura = new LecturaSensor();
        lectura.setSensor(sensor);
        lectura.setValor(dto.getValor());
        lectura.setFechaHoraLectura(LocalDateTime.now());
        verificarYEnviarAlarma(lectura);
        // Evaluar alarma
        if (sensor.getAlarmaActiva()) {
            Double valor = dto.getValor();
            if (valor < sensor.getValorMinimo() || valor > sensor.getValorMaximo()) {
                System.out.println("⚠️ Alerta: Valor fuera de rango para sensor " + sensor.getId());
            }
        }

        return lecturaSensorRepository.save(lectura);
    }

    public void verificarYEnviarAlarma(LecturaSensor lectura) {
        Sensor sensor = lectura.getSensor();
        if (lectura.getValor() < sensor.getValorMinimo() || lectura.getValor() > sensor.getValorMaximo()) {
            // Aquí agregas el correo destino, puede ser fijo o configurable
            mqttPublisher.publicarAlerta(sensor, lectura);
        }
    }

    @Override
    public List<LecturaSensor> obtenerLecturas(Long sensorId) {
        return lecturaSensorRepository.findBySensorId(sensorId);
    }
}
