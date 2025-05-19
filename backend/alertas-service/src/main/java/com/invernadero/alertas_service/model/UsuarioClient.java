package com.invernadero.alertas_service.model;

import com.invernadero.alertas_service.dto.UsuarioDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "usuario-service", url = "${servicios.usuarios.url}")
public interface UsuarioClient {

    @GetMapping("/usuarios/alerta/{id}")
    UsuarioDTO obtenerUsuarioPorId(
            @PathVariable("id") Long id);
}

