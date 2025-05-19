package com.invernadero.invernadero_service.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttProperties {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    public String getBrokerUrl() {
        return brokerUrl;
    }
}
