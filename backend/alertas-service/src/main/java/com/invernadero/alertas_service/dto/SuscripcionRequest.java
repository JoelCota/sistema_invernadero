package com.invernadero.alertas_service.dto;


import java.util.UUID;

public class SuscripcionRequest {

    private UUID usuarioId;
    private String tipoSensor;
    private String canal;

    public SuscripcionRequest() {}

    public SuscripcionRequest(UUID usuarioId, String tipoSensor, String canal) {
        this.usuarioId = usuarioId;
        this.tipoSensor = tipoSensor;
        this.canal = canal;
    }

    // Getters y setters

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }
}
